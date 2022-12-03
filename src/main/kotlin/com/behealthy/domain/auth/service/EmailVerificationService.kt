package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.dto.EmailVerificationDto
import com.behealthy.domain.auth.entity.EmailVerification
import com.behealthy.domain.auth.repository.EmailVerificationRepository
import com.behealthy.domain.auth.type.EmailVerificationPurpose
import com.behealthy.domain.email.dto.SendEmailRequest
import com.behealthy.domain.email.service.EmailSender
import com.behealthy.exception.AuthenticationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.MessageFormat
import java.time.format.DateTimeFormatter
import javax.annotation.Resource

@Service
class EmailVerificationService(
    private val repository: EmailVerificationRepository,
    private val emailSender: EmailSender
) {

    @Resource
    lateinit var self: EmailVerificationService
    fun request(emailVerificationDto: EmailVerificationDto): VerificationCode {
        val verificationCode = VerificationCode.generate()
        self.saveOrUpdateEmailVerification(emailVerificationDto, verificationCode)
        emailSender.send(createEmailRequest(emailVerificationDto.email, verificationCode))
        return verificationCode
    }

    @Transactional
    fun saveOrUpdateEmailVerification(emailVerificationDto: EmailVerificationDto, verificationCode: VerificationCode) {
        val purpose = EmailVerification.Purpose.of(emailVerificationDto.purpose)
        repository.findFirstByEmailAndPurpose(emailVerificationDto.email, purpose)
            ?.updateEmailVerification(verificationCode)
            ?: repository.save(
                EmailVerification(
                    email = emailVerificationDto.email,
                    purpose = purpose,
                    verificationCode = verificationCode.code,
                    expiredAt = verificationCode.expireAt
                )
            )
    }

    fun verify(emailVerificationDto: EmailVerificationDto, code: String) {
        repository.findFirstByEmailAndPurpose(
            email = emailVerificationDto.email,
            purpose = EmailVerification.Purpose.of(emailVerificationDto.purpose)
        )
            ?.let { VerificationCode(it.verificationCode, it.expiredAt) }
            ?.takeIf { verificationCode -> verificationCode.isVerify(code) }
            ?: throw AuthenticationException.EmailVerificationException()
    }

    private fun EmailVerification.updateEmailVerification(verificationCode: VerificationCode) {
        this.verificationCode = verificationCode.code
        this.expiredAt = verificationCode.expireAt
    }

    private fun createEmailRequest(email: String, verificationCode: VerificationCode): SendEmailRequest {
        return SendEmailRequest(
            email,
            "[Be-Healthy] 인증번호",
            MessageFormat.format(
                EMAIL_CONTENT,
                verificationCode.code,
                verificationCode.expireAt.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"))
            )
        )
    }

    private fun EmailVerification.Purpose.Companion.of(emailVerificationPurpose: EmailVerificationPurpose) =
        when (emailVerificationPurpose) {
            EmailVerificationPurpose.SIGN_UP -> EmailVerification.Purpose.SIGN_UP
            EmailVerificationPurpose.CHANGE_PASSWORD -> EmailVerification.Purpose.CHANGE_PASSWORD
        }

    companion object {
        val EMAIL_CONTENT = """
            behealthy 이메일 인증번호 입니다.
            [{0}]
            인증번호는 {1}까지 유효합니다.
        """.trimIndent()
    }
}
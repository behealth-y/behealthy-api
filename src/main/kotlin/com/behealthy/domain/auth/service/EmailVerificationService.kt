package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.entity.EmailVerification
import com.behealthy.domain.auth.repository.EmailVerificationRepository
import com.behealthy.domain.email.dto.SendEmailRequest
import com.behealthy.domain.email.service.EmailSender
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

    fun request(email: String): VerificationCode {
        val verificationCode = VerificationCode.generate()
        self.saveOrUpdateEmailVerification(email, verificationCode)
        emailSender.send(createEmailRequest(email, verificationCode))
        return verificationCode
    }

    @Transactional
    fun saveOrUpdateEmailVerification(email: String, verificationCode: VerificationCode) {
        repository.findById(email).ifPresentOrElse(
            { it.updateEmailVerification(verificationCode) },
            { repository.save(EmailVerification(email, verificationCode.code, verificationCode.expiredAt)) }
        )
    }

    private fun EmailVerification.updateEmailVerification(verificationCode: VerificationCode) {
        this.verificationCode = verificationCode.code
        this.expiredAt = verificationCode.expiredAt
    }

    private fun createEmailRequest(email: String, verificationCode: VerificationCode): SendEmailRequest {
        return SendEmailRequest(
            email,
            "[Be-Healthy] 인증번호",
            MessageFormat.format(
                EMAIL_CONTENT,
                verificationCode.code,
                verificationCode.expiredAt.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"))
            )
        )
    }

    companion object {
        val EMAIL_CONTENT = """
            behealthy 이메일 인증번호 입니다.
            [{0}]
            인증번호는 {1}까지 유효합니다.
        """.trimIndent()
    }
}
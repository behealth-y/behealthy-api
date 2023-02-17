package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.controller.dto.EmailPasswordUserCreationRequest
import com.behealthy.domain.auth.dto.EmailVerificationDto
import com.behealthy.domain.auth.entity.UserRefreshToken
import com.behealthy.domain.auth.repository.UserRefreshTokenRepository
import com.behealthy.domain.auth.type.EmailVerificationPurpose
import com.behealthy.exception.AuthenticationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthService(
    private val emailPasswordUserService: EmailPasswordUserService,
    private val emailVerificationService: EmailVerificationService,
    private val userRefreshTokenRepository: UserRefreshTokenRepository
) {

    fun signUp(request: EmailPasswordUserCreationRequest) {
        try {
            emailVerificationService.verify(
                emailVerificationDto = EmailVerificationDto(request.email, EmailVerificationPurpose.SIGN_UP),
                code = request.emailVerificationCode
            )
            emailPasswordUserService.create(request)
        } catch (e: DataIntegrityViolationException) {
            throw AuthenticationException.AlreadyExistEmailException()
        }
    }

    @Transactional
    fun reissueRefreshToken(userId: Long, refreshToken: String) {
        val userRefreshToken = getRefreshToken(userId).orElse(UserRefreshToken(userId, refreshToken))
        userRefreshToken.refreshToken = refreshToken
        userRefreshTokenRepository.save(userRefreshToken)
    }

    fun raiseIfNotValidRefreshToken(userId: Long, refreshToken: String) {
        getRefreshToken(userId)
            .takeIf { it.isPresent }
            ?.get()
            ?.takeIf { it.refreshToken == refreshToken }
            ?: throw AuthenticationException.InvalidRefreshTokenException()
    }

    private fun getRefreshToken(userId: Long): Optional<UserRefreshToken> {
        return userRefreshTokenRepository.findById(userId)
    }
}
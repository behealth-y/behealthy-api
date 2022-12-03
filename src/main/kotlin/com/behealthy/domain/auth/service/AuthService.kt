package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.dto.EmailPasswordUserCreationRequest
import com.behealthy.domain.auth.dto.EmailVerificationDto
import com.behealthy.domain.auth.type.EmailVerificationPurpose
import com.behealthy.exception.AuthenticationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val emailPasswordUserService: EmailPasswordUserService,
    private val emailVerificationService: EmailVerificationService
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
}
package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.dto.EmailPasswordUserCreationRequest
import com.behealthy.exception.AuthenticationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val emailPasswordUserService: EmailPasswordUserService,
    private val emailVerificationService: EmailVerificationService
) {

    fun signUp(emailPasswordUserCreationDto: EmailPasswordUserCreationRequest) {
        try {
            emailVerificationService.verify(emailPasswordUserCreationDto)
            emailPasswordUserService.create(emailPasswordUserCreationDto)
        } catch (e: DataIntegrityViolationException) {
            throw AuthenticationException.AlreadyExistEmailException()
        }
    }
}
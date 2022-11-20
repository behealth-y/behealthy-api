package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.dto.EmailPasswordUserCreationRequest
import com.behealthy.exception.AuthenticationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val emailPasswordUserService: EmailPasswordUserService
) {

    fun signUp(emailPasswordUserCreationDto: EmailPasswordUserCreationRequest) {
        try {
            emailPasswordUserService.create(emailPasswordUserCreationDto)
        } catch (e: DataIntegrityViolationException) {
            throw AuthenticationException.AlreadyExistEmailException()
        }
    }
}
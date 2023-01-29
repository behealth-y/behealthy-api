package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.controller.dto.EmailPasswordChangeRequest
import com.behealthy.domain.auth.controller.dto.EmailPasswordUserCreationRequest
import com.behealthy.domain.auth.dto.EmailPasswordAuthenticationUser
import com.behealthy.domain.auth.dto.EmailVerificationDto
import com.behealthy.domain.auth.entity.EmailPasswordUser
import com.behealthy.domain.auth.repository.EmailPasswordUserRepository
import com.behealthy.domain.auth.type.EmailVerificationPurpose
import com.behealthy.domain.user.dto.UserCreationDto
import com.behealthy.domain.user.entity.UserEntity
import com.behealthy.domain.user.service.UserService
import com.behealthy.exception.EmailPasswordUserException
import com.behealthy.exception.UserException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Service
class EmailPasswordUserService(
    private val repository: EmailPasswordUserRepository,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val emailVerificationService: EmailVerificationService
) : UserDetailsService {

    @Resource
    lateinit var self: EmailPasswordUserService

    @Transactional
    fun create(emailPasswordUserCreationDto: EmailPasswordUserCreationRequest) {
        raiseIfDuplicatedEmail(emailPasswordUserCreationDto.email)
        val user = userService.create(UserCreationDto(emailPasswordUserCreationDto.name))
        repository.save(
            EmailPasswordUser(
                userId = user.id!!,
                email = emailPasswordUserCreationDto.email,
                password = passwordEncoder.encode(emailPasswordUserCreationDto.password)
            )
        )
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val emailPasswordUser = getOrRaiseIfNotExist(username)
        val user = userService.findOfRaiseIfNotExist(emailPasswordUser.userId)
        if (user.status == UserEntity.Status.WITHDRAW) throw UserException.WithdrawUserException()
        return EmailPasswordAuthenticationUser(emailPasswordUser.email, emailPasswordUser.password, user)
    }

    @Transactional
    fun changePassword(request: EmailPasswordChangeRequest) {
        emailVerificationService.verify(
            emailVerificationDto = EmailVerificationDto(request.email, EmailVerificationPurpose.CHANGE_PASSWORD),
            code = request.emailVerificationCode
        )
        val emailPasswordUser = getOrRaiseIfNotExist(request.email)
        emailPasswordUser.password = passwordEncoder.encode(request.toBePassword)
    }

    fun raiseIfDuplicatedEmail(email: String) {
        get(email)?.also { throw EmailPasswordUserException.DuplicatedEmailException() }
    }

    fun getOrRaiseIfNotExist(email: String): EmailPasswordUser {
        return get(email) ?: throw UserException.NotFoundException()
    }

    fun get(email: String): EmailPasswordUser? {
        return repository.findFirstByEmail(email)
    }
}
package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.dto.EmailPasswordAuthenticationUser
import com.behealthy.domain.auth.dto.EmailPasswordUserCreationRequest
import com.behealthy.domain.auth.entity.EmailPasswordUser
import com.behealthy.domain.auth.repository.EmailPasswordUserRepository
import com.behealthy.domain.user.dto.UserCreationDto
import com.behealthy.domain.user.service.UserService
import com.behealthy.exception.UserException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmailPasswordUserService(
    private val repository: EmailPasswordUserRepository,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    @Transactional
    fun create(emailPasswordUserCreationDto: EmailPasswordUserCreationRequest) {
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
        val emailPasswordUser = get(username)
        val user = userService.find(emailPasswordUser.userId).get()
        return EmailPasswordAuthenticationUser(emailPasswordUser.email, emailPasswordUser.password, user)
    }

    fun get(email: String): EmailPasswordUser {
        return repository.findFirstByEmail(email).orElseThrow { UserException.NotFoundException() }
    }
}
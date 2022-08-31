package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.dto.EmailPasswordUserCreationDto
import com.behealthy.domain.auth.entity.EmailPasswordUser
import com.behealthy.domain.auth.repository.EmailPasswordUserRepository
import com.behealthy.domain.user.dto.UserCreationDto
import com.behealthy.domain.user.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmailPasswordUserService(
    private val repository: EmailPasswordUserRepository,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun create(emailPasswordUserCreationDto: EmailPasswordUserCreationDto) {
        val user = userService.create(UserCreationDto(emailPasswordUserCreationDto.name))
        repository.save(
            EmailPasswordUser(
                userId = user.id!!,
                email = emailPasswordUserCreationDto.email,
                password = passwordEncoder.encode(emailPasswordUserCreationDto.password)
            )
        )
    }
}
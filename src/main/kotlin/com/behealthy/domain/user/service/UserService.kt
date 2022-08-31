package com.behealthy.domain.user.service

import com.behealthy.domain.user.dto.UserCreationDto
import com.behealthy.domain.user.entity.User
import com.behealthy.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repository: UserRepository) {

    @Transactional
    fun create(userCreationDto: UserCreationDto): User {
        return repository.save(User(name = userCreationDto.name))
    }
}
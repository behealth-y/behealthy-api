package com.behealthy.domain.user.service

import com.behealthy.domain.user.dto.UserCreationDto
import com.behealthy.domain.user.entity.UserEntity
import com.behealthy.domain.user.repository.UserRepository
import com.behealthy.domain.user.vo.User
import com.behealthy.exception.UserException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repository: UserRepository) {

    @Transactional
    fun create(userCreationDto: UserCreationDto): UserEntity {
        return repository.save(UserEntity(name = userCreationDto.name))
    }

    @Transactional
    fun withdraw(id: Long) {
        val user = User(findOfRaiseIfNotExist(id))
        user.withdraw()
    }

    @Transactional(readOnly = true)
    fun findOfRaiseIfNotExist(id: Long): UserEntity {
        return repository.findById(id).orElseThrow { UserException.NotFoundException() }
    }

    @Transactional
    fun changeName(id: Long, name: String) {
        User(findOfRaiseIfNotExist(id)).changeName(name)
    }
}
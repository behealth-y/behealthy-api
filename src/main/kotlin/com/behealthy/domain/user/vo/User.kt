package com.behealthy.domain.user.vo

import com.behealthy.domain.user.entity.UserEntity

class User(private val userEntity: UserEntity) {
    val id: Long = userEntity.id!!
    val name: String get() = userEntity.name
    val status get() = userEntity.status

    fun withdraw() {
        userEntity.status = UserEntity.Status.WITHDRAW
    }

    fun changeName(name: String) {
        userEntity.name = name
    }
}
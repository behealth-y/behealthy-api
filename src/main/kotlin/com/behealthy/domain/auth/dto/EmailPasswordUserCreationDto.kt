package com.behealthy.domain.auth.dto

data class EmailPasswordUserCreationDto(
    val email: String,
    val password: String,
    val name: String
)
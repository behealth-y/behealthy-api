package com.behealthy.domain.auth.dto

data class EmailPasswordUserCreationRequest(
    val email: String,
    val password: String,
    val name: String
)
package com.behealthy.domain.auth.controller.dto

data class EmailPasswordUserCreationRequest(
    val email: String,
    val password: String,
    val name: String,
    val emailVerificationCode: String
)

data class EmailPasswordChangeRequest(
    val email: String,
    val toBePassword: String,
    val emailVerificationCode: String
)
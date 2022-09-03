package com.behealthy.domain.auth.dto

data class EmailPasswordAuthenticationRequest(
    val email: String,
    val password: String
)

data class AuthenticationResponse(
    val token: String
)
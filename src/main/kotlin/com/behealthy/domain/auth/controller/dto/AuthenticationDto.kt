package com.behealthy.domain.auth.controller.dto

data class EmailPasswordAuthenticationRequest(
    val email: String,
    val password: String
)

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)

class AuthenticationByRefreshTokenRequest(
    val refreshToken: String
)

class AuthenticationByRefreshTokenResponse(
    val accessToken: String
)

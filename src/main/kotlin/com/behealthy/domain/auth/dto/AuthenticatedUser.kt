package com.behealthy.domain.auth.dto


data class AuthenticatedUser(
    val userId: Long,
    val name: String
)
package com.behealthy.domain.auth.dto

import com.behealthy.domain.auth.type.EmailVerificationPurpose

data class EmailVerificationDto(
    val email: String,
    val purpose: EmailVerificationPurpose
)
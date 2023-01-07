package com.behealthy.domain.auth.controller.dto

import com.behealthy.domain.auth.type.EmailVerificationPurpose

class EmailVerificationRequest(
    val email: String,
    val purpose: EmailVerificationPurpose
)

class EmailVerificationCodeRequest(
    val email: String,
    val purpose: EmailVerificationPurpose,
    val emailVerificationCode: String
)

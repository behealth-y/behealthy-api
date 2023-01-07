package com.behealthy.domain.auth.controller.dto

import com.behealthy.domain.auth.type.EmailVerificationPurpose
import java.time.LocalDateTime

class EmailVerificationRequest(
    val email: String,
    val purpose: EmailVerificationPurpose
)

class EmailVerificationCodeRequest(
    val email: String,
    val purpose: EmailVerificationPurpose,
    val emailVerificationCode: String
)

class EmailVerificationResponse(
    val expireAt: LocalDateTime
)
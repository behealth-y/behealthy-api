package com.behealthy.domain.auth.service

import java.time.LocalDateTime
import kotlin.random.Random

data class VerificationCode(
    val code: String,
    val expireAt: LocalDateTime
) {

    fun isVerify(code: String) = this.code == code && expireAt <= LocalDateTime.now()

    companion object {
        fun generate(availableMinute: Long = 3): VerificationCode {
            return VerificationCode(
                code = "${Random.nextInt(100, 999)}${Random.nextInt(100, 999)}",
                expireAt = LocalDateTime.now().plusMinutes(availableMinute)
            )
        }
    }
}
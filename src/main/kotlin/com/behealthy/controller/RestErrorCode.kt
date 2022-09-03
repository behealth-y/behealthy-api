package com.behealthy.controller

import org.springframework.http.HttpStatus

sealed interface RestErrorCode {
    val httpStatus: HttpStatus
    val reason: String?
}

enum class AuthenticationErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) :
    RestErrorCode {
    ERR_UNAUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다.")
}

enum class UserErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) : RestErrorCode {
    ERR_NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
}
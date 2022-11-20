package com.behealthy.controller

import org.springframework.http.HttpStatus

sealed interface RestErrorCode {
    val httpStatus: HttpStatus
    val reason: String?
}

enum class CommonErrorCode(override val httpStatus: HttpStatus, override val reason: String?) : RestErrorCode {
    ERR_INTERVAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다");
}

enum class AuthenticationErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) :
    RestErrorCode {
    ERR_UNAUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ERR_ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다.")
}

enum class UserErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) : RestErrorCode {
    ERR_NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
}
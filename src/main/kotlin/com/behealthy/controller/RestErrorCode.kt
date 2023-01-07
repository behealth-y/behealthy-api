package com.behealthy.controller

import org.springframework.http.HttpStatus

sealed interface RestErrorCode {
    val httpStatus: HttpStatus
    val reason: String?
}

enum class ControllerErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) :
    RestErrorCode {
    ERR_NOT_FOUND(HttpStatus.NOT_FOUND),
    ERR_INTERVAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다");
}

enum class AuthenticationErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) :
    RestErrorCode {
    ERR_UNAUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ERR_ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    ERR_FAILED_EMAIL_VERIFICATION(HttpStatus.BAD_REQUEST, "이메일 인증에 실패했습니다.")
}

enum class UserErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) : RestErrorCode {
    ERR_NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
}

enum class EmailPasswordUserErrorCode(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val reason: String? = null
) : RestErrorCode {
    ERR_DUPLICATED_EMAIL(reason = "중복된 이메일입니다.")
}

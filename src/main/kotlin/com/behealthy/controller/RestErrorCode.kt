package com.behealthy.controller

import org.springframework.http.HttpStatus

sealed interface RestErrorCode {
    val name: String
    val httpStatus: HttpStatus
    val reason: String?
}

enum class ControllerErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) :
    RestErrorCode {
    ERR_NOT_FOUND(HttpStatus.NOT_FOUND),
    ERR_INTERVAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다");
}

enum class CommonErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) :
    RestErrorCode {
    ERR_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원하지 않는 요청입니다."),
    ERR_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.")
}

enum class AuthenticationErrorCode(override val httpStatus: HttpStatus, override val reason: String? = null) :
    RestErrorCode {
    ERR_UNAUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ERR_ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    ERR_FAILED_EMAIL_VERIFICATION(HttpStatus.BAD_REQUEST, "이메일 인증에 실패했습니다.")
}

enum class UserErrorCode(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val reason: String? = null
) : RestErrorCode {
    ERR_NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    ERR_WITHDRAW_USER(reason = "탈퇴한 유저입니다.")
}

enum class EmailPasswordUserErrorCode(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val reason: String? = null
) : RestErrorCode {
    ERR_DUPLICATED_EMAIL(reason = "중복된 이메일입니다.")
}

enum class WorkoutGoalErrorCode(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val reason: String? = null
) : RestErrorCode {
    ERR_INVALID_MINUTE(reason = "목표 운동 시간의 분은 0~59 이여야합니다"),
    ERR_INVALID_HOUR(reason = "목표 운동 시간의 시는 0분 이상이여야합니다"),
    ERR_GOAL_TIME_CAN_NOT_ZERO(reason = "목표 운동 시간은 최소 1분 이상이여야합니다"),
}

enum class WorkoutLogErrorCode(
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val reason: String? = null
) : RestErrorCode {
    ERR_INVALID_TIME(reason = "운동 종료 시각은 시작 시각 이후여야합니다."),
}
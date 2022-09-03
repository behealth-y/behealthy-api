package com.behealthy.controller.dto

import com.behealthy.controller.RestErrorCode

data class ErrorResponse(
    val errorCode: RestErrorCode
) {
    val reason: String? = errorCode.reason
}
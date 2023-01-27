package com.behealthy.controller.dto

import com.behealthy.controller.RestErrorCode

data class ErrorResponse(
    val errorCode: String,
    val reason: String?
) {
    constructor(errorCode: RestErrorCode) : this(errorCode.name, errorCode.reason)
}
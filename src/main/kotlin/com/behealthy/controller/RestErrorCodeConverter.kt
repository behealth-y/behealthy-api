package com.behealthy.controller

import com.behealthy.exception.AuthenticationException
import com.behealthy.exception.CustomException
import com.behealthy.exception.UserException

object RestErrorCodeConverter {
    fun convert(exception: CustomException): RestErrorCode = when (exception) {
        is AuthenticationException -> convert(exception)
        is UserException -> convert(exception)
    }

    private fun convert(exception: AuthenticationException) = when (exception) {
        is AuthenticationException.UnAuthenticatedUserException -> AuthenticationErrorCode.ERR_UNAUTHENTICATED_USER
    }

    private fun convert(exception: UserException) = when (exception) {
        is UserException.NotFoundException -> UserErrorCode.ERR_NOT_FOUND_USER
    }
}
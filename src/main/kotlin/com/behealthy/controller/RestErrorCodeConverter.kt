package com.behealthy.controller

import com.behealthy.exception.*

object RestErrorCodeConverter {
    fun convert(exception: CustomException): RestErrorCode = when (exception) {
        is AuthenticationException -> convert(exception)
        is UserException -> convert(exception)
        is ControllerException -> convert(exception)
        is EmailPasswordUserException -> convert(exception)
    }

    private fun convert(exception: AuthenticationException) = when (exception) {
        is AuthenticationException.UnAuthenticatedUserException -> AuthenticationErrorCode.ERR_UNAUTHENTICATED_USER
        is AuthenticationException.AlreadyExistEmailException -> AuthenticationErrorCode.ERR_ALREADY_EXIST_EMAIL
        is AuthenticationException.EmailVerificationException -> AuthenticationErrorCode.ERR_FAILED_EMAIL_VERIFICATION
    }

    private fun convert(exception: UserException) = when (exception) {
        is UserException.NotFoundException -> UserErrorCode.ERR_NOT_FOUND_USER
    }

    private fun convert(exception: EmailPasswordUserException) = when (exception) {
        is EmailPasswordUserException.DuplicatedEmailException -> EmailPasswordUserErrorCode.ERR_DUPLICATED_EMAIL
    }

    private fun convert(exception: ControllerException) = when (exception) {
        is ControllerException.NotFoundException -> ControllerErrorCode.ERR_NOT_FOUND
    }
}
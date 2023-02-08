package com.behealthy.controller

import com.behealthy.exception.*

object RestErrorCodeConverter {
    fun convert(exception: CustomException): RestErrorCode = when (exception) {
        is NotSupportedException -> CommonErrorCode.ERR_NOT_SUPPORTED
        is IllegalArgumentException -> CommonErrorCode.ERR_BAD_REQUEST
        is AccessDeniedException -> CommonErrorCode.ERR_ACCESS_DENIED
        is AuthenticationException -> convert(exception)
        is UserException -> convert(exception)
        is ControllerException -> convert(exception)
        is EmailPasswordUserException -> convert(exception)
        is WorkoutGoalException -> convert(exception)
        is WorkoutLogException -> convert(exception)
    }

    private fun convert(exception: AuthenticationException) = when (exception) {
        is AuthenticationException.UnAuthenticatedUserException -> AuthenticationErrorCode.ERR_UNAUTHENTICATED_USER
        is AuthenticationException.AlreadyExistEmailException -> AuthenticationErrorCode.ERR_ALREADY_EXIST_EMAIL
        is AuthenticationException.EmailVerificationException -> AuthenticationErrorCode.ERR_FAILED_EMAIL_VERIFICATION
    }

    private fun convert(exception: UserException) = when (exception) {
        is UserException.NotFoundException -> UserErrorCode.ERR_NOT_FOUND_USER
        is UserException.WithdrawUserException -> UserErrorCode.ERR_WITHDRAW_USER
    }

    private fun convert(exception: EmailPasswordUserException) = when (exception) {
        is EmailPasswordUserException.DuplicatedEmailException -> EmailPasswordUserErrorCode.ERR_DUPLICATED_EMAIL
    }

    private fun convert(exception: ControllerException) = when (exception) {
        is ControllerException.NotFoundException -> ControllerErrorCode.ERR_NOT_FOUND
    }

    private fun convert(exception: WorkoutGoalException) = when (exception) {
        is WorkoutGoalException.InvalidMinuteException -> WorkoutGoalErrorCode.ERR_INVALID_MINUTE
        is WorkoutGoalException.InvalidHourException -> WorkoutGoalErrorCode.ERR_INVALID_HOUR
        is WorkoutGoalException.GoalTimeCanNotZero -> WorkoutGoalErrorCode.ERR_GOAL_TIME_CAN_NOT_ZERO
    }

    private fun convert(exception: WorkoutLogException): RestErrorCode = when (exception) {
        is WorkoutLogException.InvalidTimeException -> WorkoutLogErrorCode.ERR_INVALID_TIME
        is WorkoutLogException.NotFoundException -> ControllerErrorCode.ERR_NOT_FOUND
    }
}
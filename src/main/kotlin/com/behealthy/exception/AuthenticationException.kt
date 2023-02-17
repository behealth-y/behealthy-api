package com.behealthy.exception

sealed class AuthenticationException : CustomException() {
    class UnAuthenticatedUserException : AuthenticationException()
    class AlreadyExistEmailException : AuthenticationException()

    class EmailVerificationException : AuthenticationException()

    class InvalidRefreshTokenException : AuthenticationException()
}
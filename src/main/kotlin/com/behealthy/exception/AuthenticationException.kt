package com.behealthy.exception

sealed class AuthenticationException : CustomException() {
    class UnAuthenticatedUserException : AuthenticationException()
}
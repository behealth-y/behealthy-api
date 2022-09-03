package com.behealthy.exception

sealed class UserException : CustomException() {
    class NotFoundException : UserException()
}
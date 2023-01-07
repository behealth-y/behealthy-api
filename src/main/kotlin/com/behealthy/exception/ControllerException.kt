package com.behealthy.exception

sealed class ControllerException : CustomException() {
    class NotFoundException : ControllerException()
}
package com.behealthy.exception

sealed class EmailPasswordUserException : CustomException() {
    class DuplicatedEmailException : EmailPasswordUserException()
}
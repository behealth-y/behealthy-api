package com.behealthy.exception

sealed class WorkoutLogException : CustomException() {

    class InvalidTimeException : WorkoutLogException()
    class NotFoundException : WorkoutLogException()
}
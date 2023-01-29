package com.behealthy.exception

sealed class WorkoutLogException : CustomException() {

    class InvalidTimeException : WorkoutLogException()
}
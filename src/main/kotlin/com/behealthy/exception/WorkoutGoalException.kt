package com.behealthy.exception

sealed class WorkoutGoalException : CustomException() {
    class InvalidMinuteException : WorkoutGoalException()
    class InvalidHourException : WorkoutGoalException()
    class GoalTimeCanNotZero : WorkoutGoalException()
}
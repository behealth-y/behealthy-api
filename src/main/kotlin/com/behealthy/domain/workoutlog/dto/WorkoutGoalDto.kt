package com.behealthy.domain.workoutlog.dto

import com.behealthy.exception.WorkoutGoalException

class WorkoutGoalDto(
    val hour: Int,
    val minute: Int
) {
    init {
        if (hour == 0 && minute == 0) throw WorkoutGoalException.GoalTimeCanNotZero()
        if (hour < 0) throw WorkoutGoalException.InvalidHourException()
        if (minute !in 0..59) throw WorkoutGoalException.InvalidMinuteException()
    }
}
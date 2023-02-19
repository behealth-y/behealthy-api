package com.behealthy.domain.workoutlog.controller.dto

import java.time.DayOfWeek

class WorkoutLogStatResponse(
    val workoutGoal: WorkoutGoalGetResponse,
    val todayWorkoutTime: Long,
    val avgWorkoutTimeInCurrentWeek: Long,
    val workoutTimes: List<WorkoutTime>
) {
    class WorkoutTime(
        val dayOfWeek: DayOfWeek,
        val workoutTime: Long
    )
}
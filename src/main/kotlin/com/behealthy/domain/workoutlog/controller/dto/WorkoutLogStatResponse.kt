package com.behealthy.domain.workoutlog.controller.dto

import java.time.LocalDate

class WorkoutLogStatResponse(
    val workoutGoal: WorkoutGoalGetResponse,
    val todayWorkoutTime: Long,
    val avgWorkoutTimeInCurrentWeek: Long,
    val workoutTimesInCurrentWeek: List<WorkoutTime>
) {
    class WorkoutTime(
        val date: LocalDate,
        val workoutTime: Long
    )
}
package com.behealthy.domain.workoutlog.controller.dto

class WorkoutLogStatResponse(
    val workoutGoal: WorkoutGoalGetResponse,
    val todayWorkoutTime: Long,
    val avgWorkoutTimeInWeek: Long
)
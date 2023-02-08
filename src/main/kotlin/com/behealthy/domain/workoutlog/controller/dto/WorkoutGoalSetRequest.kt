package com.behealthy.domain.workoutlog.controller.dto

class WorkoutGoalSetRequest(
    val hour: Int,
    val minute: Int
)

class WorkoutGoalGetResponse(
    val hour: Int,
    val minute: Int
)
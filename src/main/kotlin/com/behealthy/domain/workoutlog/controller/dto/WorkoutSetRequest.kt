package com.behealthy.domain.workoutlog.controller.dto

class WorkoutSetRequest(
    val hour: Int,
    val minute: Int
)

class WorkoutGetResponse(
    val hour: Int,
    val minute: Int
)
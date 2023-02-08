package com.behealthy.domain.workoutlog.controller.dto

import com.behealthy.domain.workoutlog.type.WorkoutLogIntensity
import java.time.LocalDate
import java.time.LocalTime

class WorkoutLogResponse(
    val workoutLogId: Long,
    val name: String,
    val emoji: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val intensity: WorkoutLogIntensity,
    val comment: String?,
)
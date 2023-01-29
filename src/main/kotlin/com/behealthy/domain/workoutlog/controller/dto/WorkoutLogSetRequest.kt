package com.behealthy.domain.workoutlog.controller.dto

import com.behealthy.domain.workoutlog.type.WorkoutLogIntensity
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalTime

class WorkoutLogSetRequest(
    val name: String,
    val emoji: String,
    val date: LocalDate,
    @field: DateTimeFormat(pattern = "HH:mm:SS")
    val startTime: LocalTime,
    @field: DateTimeFormat(pattern = "HH:mm:SS")
    val endTime: LocalTime,
    val intensity: WorkoutLogIntensity,
    val comment: String?
)
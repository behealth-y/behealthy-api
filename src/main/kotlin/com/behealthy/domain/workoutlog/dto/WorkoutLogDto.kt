package com.behealthy.domain.workoutlog.dto

import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity
import com.behealthy.domain.workoutlog.type.WorkoutLogIntensity
import com.behealthy.exception.WorkoutLogException
import java.time.LocalDate
import java.time.LocalTime

class WorkoutLogDto(
    val userId: Long,
    val name: String,
    val emoji: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val intensity: WorkoutLogIntensity,
    val comment: String?
) {
    init {
        if (startTime >= endTime) throw WorkoutLogException.InvalidTimeException()
    }

    fun toWorkoutLogEntity() = WorkoutLogEntity(
        id = null,
        userId = userId,
        name = name,
        emoji = emoji,
        date = date,
        startTime = startTime,
        endTime = endTime,
        intensity = when (intensity) {
            WorkoutLogIntensity.EASY -> WorkoutLogEntity.Intensity.EASY
            WorkoutLogIntensity.NORMAL -> WorkoutLogEntity.Intensity.NORMAL
            WorkoutLogIntensity.HARD -> WorkoutLogEntity.Intensity.HARD
            WorkoutLogIntensity.EXTREMELY_HARD -> WorkoutLogEntity.Intensity.EXTREMELY_HARD
        },
        comment = comment
    )
}
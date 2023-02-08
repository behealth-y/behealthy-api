package com.behealthy.domain.workoutlog.vo

import com.behealthy.domain.workoutlog.dto.WorkoutLogDto
import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity
import com.behealthy.domain.workoutlog.type.WorkoutLogIntensity
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

class WorkoutLog(private val workoutLogEntity: WorkoutLogEntity) {
    val id: Long = workoutLogEntity.id!!
    val name: String get() = workoutLogEntity.name
    val emoji: String get() = workoutLogEntity.emoji
    val date: LocalDate get() = workoutLogEntity.date
    val startTime: LocalTime get() = workoutLogEntity.startTime
    val endTime: LocalTime get() = workoutLogEntity.endTime
    val intensity: WorkoutLogIntensity
        get() = when (workoutLogEntity.intensity) {
            WorkoutLogEntity.Intensity.EASY -> WorkoutLogIntensity.EASY
            WorkoutLogEntity.Intensity.NORMAL -> WorkoutLogIntensity.NORMAL
            WorkoutLogEntity.Intensity.HARD -> WorkoutLogIntensity.HARD
            WorkoutLogEntity.Intensity.EXTREMELY_HARD -> WorkoutLogIntensity.EXTREMELY_HARD
        }

    val workoutTime = Duration.between(startTime, endTime).toMinutes()

    fun modify(workoutLogDto: WorkoutLogDto) = workoutLogEntity.apply {
        date = workoutLogDto.date
        startTime = workoutLogDto.startTime
        endTime = workoutLogDto.endTime
        emoji = workoutLogDto.emoji
        intensity = workoutLogDto.intensity.workoutLogEntityIntensity
        comment = workoutLogDto.comment
        name = workoutLogDto.name
    }
}
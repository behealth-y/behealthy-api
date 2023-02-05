package com.behealthy.domain.workoutlog.vo

import com.behealthy.domain.workoutlog.dto.WorkoutLogDto
import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity

class WorkoutLog(private val workoutLogEntity: WorkoutLogEntity) {

    fun modify(workoutLogDto: WorkoutLogDto) = workoutLogEntity.apply {
        date = workoutLogDto.date
        startTime = workoutLogDto.startTime
        endTime = workoutLogDto.endTime
        emoji = workoutLogDto.emoji
        intensity = workoutLogDto.intensity.convertEntityType()
        comment = workoutLogDto.comment
        name = workoutLogDto.name
    }
}
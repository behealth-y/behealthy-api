package com.behealthy.domain.workoutlog.type

import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity

enum class WorkoutLogIntensity {
    EASY,
    NORMAL,
    HARD,
    EXTREMELY_HARD;

    fun convertEntityType() = when (this) {
        EASY -> WorkoutLogEntity.Intensity.EASY
        NORMAL -> WorkoutLogEntity.Intensity.NORMAL
        HARD -> WorkoutLogEntity.Intensity.HARD
        EXTREMELY_HARD -> WorkoutLogEntity.Intensity.EXTREMELY_HARD
    }
}
package com.behealthy.domain.workoutlog.type

import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity

enum class WorkoutLogIntensity(val workoutLogEntityIntensity: WorkoutLogEntity.Intensity) {
    EASY(WorkoutLogEntity.Intensity.EASY),
    NORMAL(WorkoutLogEntity.Intensity.NORMAL),
    HARD(WorkoutLogEntity.Intensity.HARD),
    EXTREMELY_HARD(WorkoutLogEntity.Intensity.EXTREMELY_HARD);

    companion object {
        fun from(workoutLogEntityIntensity: WorkoutLogEntity.Intensity) =
            values().first { it.workoutLogEntityIntensity == workoutLogEntityIntensity }
    }

}
package com.behealthy.domain.workoutlog.vo

import com.behealthy.domain.workoutlog.dto.WorkoutGoalDto
import com.behealthy.domain.workoutlog.entity.WorkoutGoalEntity
import java.time.LocalDateTime

class WorkoutGoal(private val workoutGoalEntity: WorkoutGoalEntity) {
    val userId: Long = workoutGoalEntity.userId
    val hour: Int get() = workoutGoalEntity.hour
    val minute: Int get() = workoutGoalEntity.minute

    fun set(request: WorkoutGoalDto) {
        workoutGoalEntity.hour = request.hour
        workoutGoalEntity.minute = request.minute
        workoutGoalEntity.modifiedAt = LocalDateTime.now()
    }
}
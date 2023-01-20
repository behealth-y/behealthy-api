package com.behealthy.domain.workoutlog.service

import com.behealthy.domain.workoutlog.dto.WorkoutGoalDto
import com.behealthy.domain.workoutlog.entity.WorkoutGoalEntity
import com.behealthy.domain.workoutlog.repository.WorkoutGoalRepository
import com.behealthy.domain.workoutlog.vo.WorkoutGoal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WorkoutGoalService(
    private val workoutGoalRepository: WorkoutGoalRepository
) {

    @Transactional
    fun setWorkoutGoal(userId: Long, request: WorkoutGoalDto) {
        getAndSaveWorkoutGoal(userId).let { WorkoutGoal(it).set(request) }
    }

    private fun getAndSaveWorkoutGoal(userId: Long): WorkoutGoalEntity {
        return workoutGoalRepository.findById(userId)
            .orElseGet { workoutGoalRepository.save(WorkoutGoalEntity(userId)) }
    }
}
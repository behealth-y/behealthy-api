package com.behealthy.domain.workoutlog.service

import com.behealthy.domain.workoutlog.dto.WorkoutLogDto
import com.behealthy.domain.workoutlog.repository.WorkoutLogRepository
import org.springframework.stereotype.Service

@Service
class WorkoutLogService(private val workoutLogRepository: WorkoutLogRepository) {

    fun create(workoutLogDto: WorkoutLogDto) {
        workoutLogRepository.save(workoutLogDto.toWorkoutLogEntity())
    }
}
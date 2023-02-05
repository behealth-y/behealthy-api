package com.behealthy.domain.workoutlog.service

import com.behealthy.domain.workoutlog.dto.WorkoutLogDto
import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity
import com.behealthy.domain.workoutlog.repository.WorkoutLogRepository
import com.behealthy.domain.workoutlog.vo.WorkoutLog
import com.behealthy.exception.WorkoutLogException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WorkoutLogService(private val workoutLogRepository: WorkoutLogRepository) {

    fun create(workoutLogDto: WorkoutLogDto) {
        workoutLogRepository.save(workoutLogDto.toWorkoutLogEntity())
    }

    @Transactional
    fun modify(workoutLogId: Long, workoutLogDto: WorkoutLogDto) {
        val workoutLog = WorkoutLog(findOneById(workoutLogId))
        workoutLog.modify(workoutLogDto)
    }

    @Transactional(readOnly = true)
    fun findOneById(workoutLogId: Long): WorkoutLogEntity {
        return workoutLogRepository.findById(workoutLogId).orElseThrow { WorkoutLogException.NotFoundException() }
    }

}
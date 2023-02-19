package com.behealthy.domain.workoutlog.service

import com.behealthy.domain.workoutlog.dto.WorkoutLogDto
import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity
import com.behealthy.domain.workoutlog.repository.WorkoutLogRepository
import com.behealthy.domain.workoutlog.vo.WorkoutLog
import com.behealthy.exception.AccessDeniedException
import com.behealthy.exception.WorkoutLogException
import com.behealthy.util.LocalDateExtension.withEndDayOfMonth
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth

@Service
class WorkoutLogService(private val workoutLogRepository: WorkoutLogRepository) {

    fun create(workoutLogDto: WorkoutLogDto): WorkoutLogEntity {
        return workoutLogRepository.save(workoutLogDto.toWorkoutLogEntity())
    }

    @Transactional
    fun modify(workoutLogId: Long, workoutLogDto: WorkoutLogDto) {
        val workoutLog = WorkoutLog(findOneById(workoutLogId))
        workoutLog.modify(workoutLogDto)
    }

    @Transactional
    fun delete(workoutLogId: Long) {
        workoutLogRepository.deleteAllById(setOf(workoutLogId))
    }

    fun findOnById(userId: Long, workoutLogId: Long): WorkoutLogEntity {
        return findOneById(workoutLogId).also {
            if (it.userId != userId) throw AccessDeniedException()
        }
    }

    @Transactional(readOnly = true)
    fun findOneById(workoutLogId: Long): WorkoutLogEntity {
        return workoutLogRepository.findById(workoutLogId).orElseThrow { WorkoutLogException.NotFoundException() }
    }

    fun findAllByYearMonth(userId: Long, yearMonth: YearMonth): List<WorkoutLog> {
        val startDayOfYearMonth = yearMonth.atDay(1)
        return workoutLogRepository.findAllByUserIdAndDateBetween(
            userId,
            startDayOfYearMonth,
            startDayOfYearMonth.withEndDayOfMonth()
        )
            .map { WorkoutLog(it) }
    }

    fun findAllByDate(userId: Long, date: LocalDate): List<WorkoutLog> {
        return workoutLogRepository.findAllByUserIdAndDate(userId, date).map { WorkoutLog(it) }
    }

    fun findAllInCurrentWeek(userId: Long, criteriaDate: LocalDate): List<WorkoutLog> {
        return workoutLogRepository.findAllByUserIdAndDateBetween(
            userId,
            criteriaDate.minusDays(criteriaDate.dayOfWeek.value.toLong() - 1),
            criteriaDate
        ).map { WorkoutLog(it) }
    }
}
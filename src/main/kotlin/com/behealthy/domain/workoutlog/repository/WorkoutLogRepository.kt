package com.behealthy.domain.workoutlog.repository

import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
interface WorkoutLogRepository : JpaRepository<WorkoutLogEntity, Long> {

    @Transactional(readOnly = true)
    fun findAllByUserIdAndDateBetween(userId: Long, startDate: LocalDate, endDate: LocalDate): List<WorkoutLogEntity>

    @Transactional(readOnly = true)
    fun findAllByUserIdAndDate(userId: Long, date: LocalDate): List<WorkoutLogEntity>
}
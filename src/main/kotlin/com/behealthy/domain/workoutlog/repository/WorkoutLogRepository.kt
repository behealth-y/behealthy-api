package com.behealthy.domain.workoutlog.repository

import com.behealthy.domain.workoutlog.entity.WorkoutLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutLogRepository : JpaRepository<WorkoutLogEntity, Long>
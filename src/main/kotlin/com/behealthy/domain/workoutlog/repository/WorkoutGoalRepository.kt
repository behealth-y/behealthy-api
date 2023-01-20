package com.behealthy.domain.workoutlog.repository

import com.behealthy.domain.workoutlog.entity.WorkoutGoalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutGoalRepository : JpaRepository<WorkoutGoalEntity, Long>
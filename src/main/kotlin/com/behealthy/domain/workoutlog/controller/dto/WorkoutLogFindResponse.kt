package com.behealthy.domain.workoutlog.controller.dto

import java.time.LocalDate

class WorkoutLogFindResponse(
    val date: LocalDate,
    val totalWorkoutTime: Long,
    val workoutLogs: List<Element>
) {
    class Element(
        val workoutLogId: Long,
        val name: String,
        val emoji: String,
        val workoutTime: Long
    )

    companion object
}
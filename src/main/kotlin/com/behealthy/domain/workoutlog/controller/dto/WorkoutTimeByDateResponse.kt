package com.behealthy.domain.workoutlog.controller.dto

import java.time.LocalDate

class WorkoutTimeByDateResponse(
    val year: Int,
    val month: Int,
    val workoutLogs: List<Element>
) {
    class Element(
        val date: LocalDate,
        val totalWorkoutTime: Long
    )

    companion object
}
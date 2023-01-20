package com.behealthy.domain.workoutlog.controller

import com.behealthy.domain.auth.dto.AuthenticatedUser
import com.behealthy.domain.workoutlog.controller.dto.WorkoutSetRequest
import com.behealthy.domain.workoutlog.dto.WorkoutGoalDto
import com.behealthy.domain.workoutlog.service.WorkoutGoalService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class WorkoutGoalController(
    private val workoutGoalService: WorkoutGoalService
) {

    @PutMapping("/workout-goal")
    fun setWorkoutGoal(@AuthenticationPrincipal user: AuthenticatedUser, @RequestBody request: WorkoutSetRequest) {
        workoutGoalService.setWorkoutGoal(user.userId, WorkoutGoalDto(request.hour, request.minute))
    }
}
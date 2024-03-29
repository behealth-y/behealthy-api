package com.behealthy.domain.workoutlog.controller

import com.behealthy.domain.auth.dto.AuthenticatedUser
import com.behealthy.domain.workoutlog.controller.dto.WorkoutGoalGetResponse
import com.behealthy.domain.workoutlog.controller.dto.WorkoutGoalSetRequest
import com.behealthy.domain.workoutlog.dto.WorkoutGoalDto
import com.behealthy.domain.workoutlog.service.WorkoutGoalService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class WorkoutGoalController(
    private val workoutGoalService: WorkoutGoalService
) {

    @Operation(summary = "목표 운동 시간 조회")
    @GetMapping("/workout-goal")
    fun getWorkoutGoal(@AuthenticationPrincipal user: AuthenticatedUser): WorkoutGoalGetResponse {
        return workoutGoalService.getAndSaveWorkoutGoal(user.userId).let { WorkoutGoalGetResponse(it.hour, it.minute) }
    }

    @Operation(summary = "목표 운동 시간 설정")
    @PutMapping("/workout-goal")
    fun setWorkoutGoal(@AuthenticationPrincipal user: AuthenticatedUser, @RequestBody request: WorkoutGoalSetRequest) {
        workoutGoalService.setWorkoutGoal(user.userId, WorkoutGoalDto(request.hour, request.minute))
    }
}
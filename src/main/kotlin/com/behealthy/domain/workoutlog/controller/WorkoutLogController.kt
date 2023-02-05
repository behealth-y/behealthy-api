package com.behealthy.domain.workoutlog.controller

import com.behealthy.domain.auth.dto.AuthenticatedUser
import com.behealthy.domain.workoutlog.controller.dto.WorkoutLogSetRequest
import com.behealthy.domain.workoutlog.dto.WorkoutLogDto
import com.behealthy.domain.workoutlog.service.WorkoutLogService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class WorkoutLogController(private val workoutLogService: WorkoutLogService) {

    @Operation(summary = "운동 기록 생성")
    @PostMapping("/workout-logs")
    fun create(@AuthenticationPrincipal user: AuthenticatedUser, @RequestBody request: WorkoutLogSetRequest) {
        workoutLogService.create(
            request.toWorkoutLogDto(user.userId)
        )
    }

    @Operation(summary = "운동 기록 수정")
    @PatchMapping("/workout-logs/{workoutLogId}")
    fun modify(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @PathVariable workoutLogId: Long,
        @RequestBody request: WorkoutLogSetRequest
    ) {
        workoutLogService.modify(workoutLogId, request.toWorkoutLogDto(user.userId))
    }

    @Operation(summary = "운동 기록 삭제")
    @DeleteMapping("/workout-logs/{workoutLogId}")
    fun delete(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @PathVariable workoutLogId: Long
    ) {
        workoutLogService.delete(workoutLogId)
    }

    private fun WorkoutLogSetRequest.toWorkoutLogDto(userId: Long) = with(this) {
        WorkoutLogDto(
            userId = userId,
            name = name,
            emoji = emoji,
            date = date,
            startTime = startTime,
            endTime = endTime,
            intensity = intensity,
            comment = comment
        )
    }

}
package com.behealthy.domain.workoutlog.controller

import com.behealthy.domain.auth.dto.AuthenticatedUser
import com.behealthy.domain.workoutlog.controller.dto.WorkoutLogSetRequest
import com.behealthy.domain.workoutlog.dto.WorkoutLogDto
import com.behealthy.domain.workoutlog.service.WorkoutLogService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class WorkoutLogController(private val workoutLogService: WorkoutLogService) {

    @Operation(summary = "운동 기록 생성")
    @PostMapping("/workout-logs")
    fun create(@AuthenticationPrincipal user: AuthenticatedUser, @RequestBody request: WorkoutLogSetRequest) {
        workoutLogService.create(
            with(request) {
                WorkoutLogDto(
                    userId = user.userId,
                    name = name,
                    emoji = emoji,
                    date = date,
                    startTime = startTime,
                    endTime = endTime,
                    intensity = intensity,
                    comment = comment
                )
            }
        )
    }
}
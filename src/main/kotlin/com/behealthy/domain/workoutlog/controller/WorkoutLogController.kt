package com.behealthy.domain.workoutlog.controller

import com.behealthy.domain.auth.dto.AuthenticatedUser
import com.behealthy.domain.workoutlog.controller.dto.*
import com.behealthy.domain.workoutlog.dto.WorkoutLogDto
import com.behealthy.domain.workoutlog.service.WorkoutLogService
import com.behealthy.domain.workoutlog.type.WorkoutLogIntensity
import com.behealthy.domain.workoutlog.vo.WorkoutLog
import io.swagger.v3.oas.annotations.Operation
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.YearMonth

@RequestMapping("/api")
@RestController
class WorkoutLogController(private val workoutLogService: WorkoutLogService) {

    @Operation(summary = "운동 기록 생성")
    @PostMapping("/workout-logs")
    fun create(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @RequestBody request: WorkoutLogSetRequest
    ): WorkoutLogSetResponse {
        val workoutLog = workoutLogService.create(request.toWorkoutLogDto(user.userId))
        return WorkoutLogSetResponse(workoutLog.id!!)
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

    @Operation(summary = "특정 년/월 기준 날짜별 운동 시간 조회")
    @GetMapping("/workout-logs/workout-time")
    fun findWorkoutTimeByDate(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @RequestParam year: Int,
        @RequestParam month: Int
    ): WorkoutTimeFindResponse {
        val yearMonth = YearMonth.of(year, month)
        val workoutLogs = workoutLogService.findAllByYearMonth(user.userId, yearMonth)
        return WorkoutTimeFindResponse.of(yearMonth, workoutLogs)
    }

    @Operation(summary = "특정 날짜의 운동 기록 조회")
    @GetMapping("/workout-logs")
    fun findWorkoutLog(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): WorkoutLogFindResponse {
        val workoutLogs = workoutLogService.findAllByDate(user.userId, date)
        return WorkoutLogFindResponse.of(date, workoutLogs)
    }

    @Operation(summary = "특정 id 운동 기록 조회")
    @GetMapping("/workout-logs/{workoutLogId}")
    fun findWorkoutLog(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @PathVariable workoutLogId: Long
    ): WorkoutLogResponse {
        val workoutLog = workoutLogService.findOnById(user.userId, workoutLogId)
        return with(workoutLog) {
            WorkoutLogResponse(
                workoutLogId = id!!,
                name = name,
                emoji = emoji,
                date = date,
                startTime = startTime,
                endTime = endTime,
                intensity = WorkoutLogIntensity.from(intensity),
                comment = comment
            )
        }
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

    private fun WorkoutTimeFindResponse.Companion.of(
        yearMonth: YearMonth,
        workoutLogs: List<WorkoutLog>
    ): WorkoutTimeFindResponse {
        return WorkoutTimeFindResponse(
            year = yearMonth.year,
            month = yearMonth.monthValue,
            workoutLogs = workoutLogs
                .groupBy { it.date }
                .mapValues { it.value.totalWorkoutTime() }
                .map { (date, totalWorkoutTime) -> WorkoutTimeFindResponse.Element(date, totalWorkoutTime) }
        )
    }

    private fun WorkoutLogFindResponse.Companion.of(
        date: LocalDate,
        workoutLogs: List<WorkoutLog>
    ): WorkoutLogFindResponse {
        return WorkoutLogFindResponse(
            date = date,
            totalWorkoutTime = workoutLogs.totalWorkoutTime(),
            workoutLogs = workoutLogs.map { WorkoutLogFindResponse.Element(it.id, it.name, it.emoji, it.workoutTime) }
        )
    }

    private fun List<WorkoutLog>.totalWorkoutTime() = sumOf { it.workoutTime }

}
package com.behealthy.domain.user.controller

import com.behealthy.domain.auth.dto.AuthenticatedUser
import com.behealthy.domain.user.controller.dto.UserNameChangeRequest
import com.behealthy.domain.user.service.UserService
import com.behealthy.exception.NotSupportedException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class UserController(private val userService: UserService) {

    @Operation(summary = "유저 탈퇴")
    @DeleteMapping("/users/{userId}")
    fun withdraw(@AuthenticationPrincipal user: AuthenticatedUser, @PathVariable userId: Long) {
        if (user.userId != userId) throw NotSupportedException()
        userService.withdraw(user.userId)
    }

    @Operation(summary = "이름 변경 ")
    @PatchMapping("/users/{userId}/name")
    fun changeName(
        @AuthenticationPrincipal user: AuthenticatedUser,
        @PathVariable userId: Long,
        @RequestBody request: UserNameChangeRequest
    ) {
        if (user.userId != userId) throw NotSupportedException()
        userService.changeName(user.userId, request.name)
    }
}
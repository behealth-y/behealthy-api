package com.behealthy.domain.user.controller

import com.behealthy.domain.auth.dto.AuthenticatedUser
import com.behealthy.domain.user.service.UserService
import com.behealthy.exception.NotSupportedException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class UserController(private val userService: UserService) {

    @Operation(summary = "유저 탈퇴")
    @DeleteMapping("/users/{userId}")
    fun withdraw(@AuthenticationPrincipal user: AuthenticatedUser, @PathVariable userId: Long) {
        if (user.userId != userId) throw NotSupportedException()
        userService.withdraw(user.userId)
    }
}
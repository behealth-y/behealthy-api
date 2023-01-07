package com.behealthy.domain.auth.controller

import com.behealthy.domain.auth.controller.dto.EmailPasswordChangeRequest
import com.behealthy.domain.auth.service.EmailPasswordUserService
import com.behealthy.exception.ControllerException.NotFoundException
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class EmailPasswordUserController(private val service: EmailPasswordUserService) {

    @Operation(summary = "비밀번호 수정")
    @PatchMapping("/auth/email-password-user/password")
    fun changePassword(@RequestBody request: EmailPasswordChangeRequest) {
        service.changePassword(request)
    }

    @Operation(summary = "유저 존재 여부", description = "유저가 존재하면 200, 존재하지 않으면 404")
    @RequestMapping(method = [RequestMethod.HEAD], value = ["/auth/email-password-user/{email}"])
    fun checkDuplicatedEmail(@PathVariable email: String) {
        service.get(email) ?: throw NotFoundException()
    }
}
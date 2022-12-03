package com.behealthy.domain.auth.controller

import com.behealthy.domain.auth.controller.dto.EmailPasswordChangeRequest
import com.behealthy.domain.auth.service.EmailPasswordUserService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class EmailPasswordUserController(private val service: EmailPasswordUserService) {

    @PatchMapping("/auth/email-password-user/password")
    fun changePassword(@RequestBody request: EmailPasswordChangeRequest) {
        service.changePassword(request)
    }
}
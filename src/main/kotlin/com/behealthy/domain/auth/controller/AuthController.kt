package com.behealthy.domain.auth.controller

import com.behealthy.domain.auth.dto.EmailPasswordUserCreationDto
import com.behealthy.domain.auth.service.EmailPasswordUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class AuthController(private val emailPasswordUserService: EmailPasswordUserService) {

    @PostMapping("/auth/signup")
    fun signup(@RequestBody emailPasswordUserCreationDto: EmailPasswordUserCreationDto): ResponseEntity<Unit> {
        emailPasswordUserService.create(emailPasswordUserCreationDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
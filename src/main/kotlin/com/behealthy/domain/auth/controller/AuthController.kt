package com.behealthy.domain.auth.controller

import com.behealthy.domain.auth.JWTUtil
import com.behealthy.domain.auth.controller.dto.AuthenticationResponse
import com.behealthy.domain.auth.controller.dto.EmailPasswordAuthenticationRequest
import com.behealthy.domain.auth.dto.EmailPasswordAuthenticationUser
import com.behealthy.domain.auth.dto.EmailPasswordUserCreationRequest
import com.behealthy.domain.auth.service.EmailPasswordUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class AuthController(
    private val emailPasswordUserService: EmailPasswordUserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JWTUtil
) {

    @PostMapping("/auth/signup")
    fun signup(
        @RequestBody
        emailPasswordUserCreationDto: EmailPasswordUserCreationRequest
    ): ResponseEntity<Unit> {
        emailPasswordUserService.create(emailPasswordUserCreationDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/auth")
    fun authentication(
        @RequestBody
        emailPasswordAuthenticationDto: EmailPasswordAuthenticationRequest
    ): AuthenticationResponse {
        val emailPasswordUserDetails = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                emailPasswordAuthenticationDto.email,
                emailPasswordAuthenticationDto.password
            )
        ).principal as EmailPasswordAuthenticationUser
        return AuthenticationResponse(jwtUtil.generateToken(emailPasswordUserDetails.user))
    }

}
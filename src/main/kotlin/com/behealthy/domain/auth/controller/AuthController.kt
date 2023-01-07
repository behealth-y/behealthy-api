package com.behealthy.domain.auth.controller

import com.behealthy.domain.auth.JWTUtil
import com.behealthy.domain.auth.controller.dto.*
import com.behealthy.domain.auth.dto.EmailPasswordAuthenticationUser
import com.behealthy.domain.auth.dto.EmailVerificationDto
import com.behealthy.domain.auth.service.AuthService
import com.behealthy.domain.auth.service.EmailVerificationService
import io.swagger.v3.oas.annotations.Operation
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
    private val authService: AuthService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JWTUtil,
    private val emailVerificationService: EmailVerificationService
) {

    @Operation(summary = "회원가입")
    @PostMapping("/auth/signup")
    fun signup(
        @RequestBody
        emailPasswordUserCreationDto: EmailPasswordUserCreationRequest
    ): ResponseEntity<Unit> {
        authService.signUp(emailPasswordUserCreationDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Operation(summary = "인증")
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

    @Operation(summary = "이메일 인증번호 발송")
    @PostMapping("/auth/email-verification/request")
    fun requestEmailVerification(@RequestBody request: EmailVerificationRequest) {
        emailVerificationService.request(EmailVerificationDto(request.email, request.purpose))
    }

    @Operation(summary = "이메일 인증번호 검증")
    @PostMapping("/auth/email-verification/verify")
    fun verifyEmailVerification(@RequestBody request: EmailVerificationCodeRequest) {
        emailVerificationService.verify(
            EmailVerificationDto(request.email, request.purpose),
            request.emailVerificationCode
        )
    }
}
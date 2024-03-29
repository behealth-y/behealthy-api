package com.behealthy.domain.auth.controller

import com.behealthy.domain.auth.JWTUtil
import com.behealthy.domain.auth.controller.dto.*
import com.behealthy.domain.auth.dto.EmailPasswordAuthenticationUser
import com.behealthy.domain.auth.dto.EmailVerificationDto
import com.behealthy.domain.auth.service.AuthService
import com.behealthy.domain.auth.service.EmailVerificationService
import com.behealthy.domain.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
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
    private val emailVerificationService: EmailVerificationService,
    private val userService: UserService
) {

    @Operation(summary = "회원가입")
    @PostMapping("/auth/signup")
    fun signup(
        @RequestBody
        emailPasswordUserCreationDto: EmailPasswordUserCreationRequest
    ): AuthenticationResponse {
        val userEntity = authService.signUp(emailPasswordUserCreationDto)
        val accessToken = jwtUtil.generateAccessToken(userEntity)
        val refreshToken = jwtUtil.generateRefreshToken(userEntity)
            .also { authService.reissueRefreshToken(userEntity.id!!, it) }
        return AuthenticationResponse(accessToken, refreshToken)
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

        val accessToken = jwtUtil.generateAccessToken(emailPasswordUserDetails.userEntity)
        val refreshToken = jwtUtil.generateRefreshToken(emailPasswordUserDetails.userEntity)
            .also { authService.reissueRefreshToken(emailPasswordUserDetails.userEntity.id!!, it) }
        return AuthenticationResponse(accessToken, refreshToken)
    }

    @Operation(summary = "인증 리프레시")
    @PostMapping("/auth/refresh")
    fun authenticationByRefreshToken(@RequestBody request: AuthenticationByRefreshTokenRequest): AuthenticationByRefreshTokenResponse {
        jwtUtil.validateToken(request.refreshToken)
        val userId = jwtUtil.extractUserId(request.refreshToken)
        authService.raiseIfNotValidRefreshToken(userId, request.refreshToken)
        return AuthenticationByRefreshTokenResponse(
            jwtUtil.generateAccessToken(userService.findOfRaiseIfNotExist(userId))
        )
    }

    @Operation(summary = "이메일 인증번호 발송")
    @PostMapping("/auth/email-verification/request")
    fun requestEmailVerification(@RequestBody request: EmailVerificationRequest): EmailVerificationResponse {
        return EmailVerificationResponse(
            emailVerificationService.request(EmailVerificationDto(request.email, request.purpose)).expireAt
        )
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
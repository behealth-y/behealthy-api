package com.behealthy.controller.advice

import com.behealthy.controller.AuthenticationErrorCode
import com.behealthy.controller.RestErrorCodeConverter
import com.behealthy.controller.dto.ErrorResponse
import com.behealthy.exception.CustomException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AuthenticationException::class)
    fun handle(e: AuthenticationException): ResponseEntity<ErrorResponse> {
        val errorCode = AuthenticationErrorCode.ERR_UNAUTHENTICATED_USER
        return ResponseEntity.status(errorCode.httpStatus).body(ErrorResponse(errorCode))
    }

    @ExceptionHandler(CustomException::class)
    fun handle(e: CustomException): ResponseEntity<ErrorResponse> {
        val errorCode = RestErrorCodeConverter.convert(e)
        return ResponseEntity.status(errorCode.httpStatus).body(ErrorResponse(errorCode))
    }
}
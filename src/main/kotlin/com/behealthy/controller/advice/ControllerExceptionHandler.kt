package com.behealthy.controller.advice

import com.behealthy.controller.AuthenticationErrorCode
import com.behealthy.controller.ControllerErrorCode
import com.behealthy.controller.RestErrorCodeConverter
import com.behealthy.controller.dto.ErrorResponse
import com.behealthy.exception.CustomException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler() {

    private val kLogger = mu.KotlinLogging.logger { }

    @ExceptionHandler(AuthenticationException::class)
    fun handle(e: AuthenticationException): ResponseEntity<ErrorResponse> {
        kLogger.error { e.message }
        val errorCode = AuthenticationErrorCode.ERR_UNAUTHENTICATED_USER
        return ResponseEntity.status(errorCode.httpStatus).body(ErrorResponse(errorCode))
    }

    @ExceptionHandler(CustomException::class)
    fun handle(e: CustomException): ResponseEntity<ErrorResponse> {
        kLogger.error { e.message }
        val errorCode = RestErrorCodeConverter.convert(e)
        return ResponseEntity.status(errorCode.httpStatus).body(ErrorResponse(errorCode))
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handle(e: Exception): ErrorResponse {
        kLogger.error { e.message }
        return ErrorResponse(ControllerErrorCode.ERR_INTERVAL_SERVER_ERROR)
    }
}
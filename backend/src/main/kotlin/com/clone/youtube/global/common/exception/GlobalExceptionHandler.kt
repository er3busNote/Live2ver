package com.clone.youtube.global.common.exception

import com.clone.youtube.global.common.response.ResponseHandler
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseHandler<Nothing> {
        return ResponseHandler.of(HttpStatus.INTERNAL_SERVER_ERROR, e.message ?: "서버 오류")
    }
}
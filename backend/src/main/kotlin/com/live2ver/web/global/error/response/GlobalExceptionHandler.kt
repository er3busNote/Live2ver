package com.live2ver.web.global.error.response

import com.live2ver.web.global.common.response.ResponseHandler
import com.live2ver.web.global.error.type.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFound(ex: UsernameNotFoundException): ResponseHandler<Nothing> {
        return ResponseHandler.of(HttpStatus.UNAUTHORIZED, ex.message ?: ErrorCode.USERNAME_NOT_FOUND_ERROR.message)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException): ResponseHandler<Nothing> {
        return ResponseHandler.of(HttpStatus.UNAUTHORIZED, ex.message ?: ErrorCode.BAD_CREDENTIAL_ERROR.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleOtherExceptions(e: Exception): ResponseHandler<Nothing> {
        if (e is NoResourceFoundException) {
            throw e // 루프 방지
        }
        return ResponseHandler.of(HttpStatus.INTERNAL_SERVER_ERROR, e.message ?: ErrorCode.INTERNAL_SERVER_ERROR.message)
    }
}
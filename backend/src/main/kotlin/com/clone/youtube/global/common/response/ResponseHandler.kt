package com.clone.youtube.global.common.response

import org.springframework.http.HttpStatus

data class ResponseHandler<T>(
    val status: Int,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun <T> of(status: HttpStatus, message: String, data: T? = null): ResponseHandler<T> {
            return ResponseHandler(
                status = status.value(),
                message = message,
                data = data
            )
        }
    }
}

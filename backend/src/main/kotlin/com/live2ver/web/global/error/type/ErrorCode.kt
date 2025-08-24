package com.live2ver.web.global.error.type

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    USERNAME_NOT_FOUND_ERROR(HttpStatus.UNAUTHORIZED, "사용자를 찾을 수 없습니다."),
    BAD_CREDENTIAL_ERROR(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류");
}
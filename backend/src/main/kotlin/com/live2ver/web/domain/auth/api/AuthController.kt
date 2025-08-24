package com.live2ver.web.domain.auth.api

import com.live2ver.web.domain.auth.dto.request.LoginRequest
import com.live2ver.web.domain.auth.dto.request.RefreshRequest
import com.live2ver.web.domain.auth.dto.response.TokenResponse
import com.live2ver.web.domain.auth.service.AuthService
import com.live2ver.web.global.common.response.ResponseHandler
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseHandler<TokenResponse> {
        return ResponseHandler.of(
            HttpStatus.OK,
            "로그인 되었습니다.",
            authService.login(request)
        )
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest): ResponseHandler<TokenResponse> {
        return ResponseHandler.of(
            HttpStatus.OK,
            "인증 유효기간이 갱신되었습니다.",
            authService.refreshToken(request)
        )
    }
}
package com.live2ver.web.domain.auth.service

import com.live2ver.web.domain.auth.dto.request.LoginRequest
import com.live2ver.web.domain.auth.dto.request.RefreshRequest
import com.live2ver.web.domain.auth.dto.response.TokenResponse
import com.live2ver.web.domain.user.repository.UserRepository
import com.live2ver.web.global.common.jwt.JwtManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val refreshTokenStore = mutableMapOf<String, String>()

    fun login(loginRequest: LoginRequest): TokenResponse {
        val user = userRepository.findByUsername(loginRequest.username)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다.")

        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw BadCredentialsException("비밀번호가 일치하지 않습니다.")
        }

        val accessToken = JwtManager.generateAccessToken(user.username)
        val refreshToken = JwtManager.generateRefreshToken(user.username)
        refreshTokenStore[user.username] = refreshToken

        return TokenResponse.of(
            accessToken,
            refreshToken
        )
    }

    fun refreshToken(refreshRequest: RefreshRequest): TokenResponse {
        val refreshToken = refreshRequest.refreshToken
        val username = JwtManager.getUsernameFromToken(refreshToken)
        val storedToken = refreshTokenStore[username]
        if (storedToken == null || storedToken != refreshToken) {
            throw IllegalArgumentException("Invalid refresh token")
        }
        return TokenResponse.of(
            JwtManager.generateAccessToken(username),
            refreshToken
        )
    }
}
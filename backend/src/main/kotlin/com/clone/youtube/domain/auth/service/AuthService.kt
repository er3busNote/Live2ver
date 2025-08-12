package com.clone.youtube.domain.auth.service

import com.clone.youtube.domain.auth.dto.request.LoginRequest
import com.clone.youtube.domain.auth.dto.request.RefreshRequest
import com.clone.youtube.domain.auth.dto.response.TokenResponse
import com.clone.youtube.domain.user.repository.UserRepository
import com.clone.youtube.global.common.jwt.JwtManager
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
        val user = userRepository.findByClerkId(loginRequest.userId)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다.")

        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw BadCredentialsException("비밀번호가 일치하지 않습니다.")
        }

        val accessToken = JwtManager.generateAccessToken(user.clerkId)
        val refreshToken = JwtManager.generateRefreshToken(user.clerkId)
        refreshTokenStore[user.clerkId] = refreshToken

        return TokenResponse.of(
            accessToken,
            refreshToken
        )
    }

    fun refreshToken(refreshRequest: RefreshRequest): TokenResponse {
        val userId = refreshRequest.userId
        val refreshToken = refreshRequest.refreshToken
        val storedToken = refreshTokenStore[userId]
        if (storedToken == null || storedToken != refreshToken) {
            throw IllegalArgumentException("Invalid refresh token")
        }
        return TokenResponse.of(
            JwtManager.generateAccessToken(userId),
            refreshToken
        )
    }
}
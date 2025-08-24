package com.live2ver.web.domain.auth

import com.live2ver.web.domain.auth.dto.request.LoginRequest
import com.live2ver.web.domain.auth.dto.response.TokenResponse
import com.live2ver.web.domain.user.entity.User
import com.live2ver.web.domain.user.repository.UserRepository
import com.live2ver.web.global.common.response.ResponseHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AuthTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val userRepository: UserRepository
) {
    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
        userRepository.save(
            User(
                username = "testuser",
                password = BCryptPasswordEncoder().encode("password"),
                name = "test"
            )
        )
    }

    @Test
    @DisplayName("로그인 및 Refresh Token 정보 확인")
    fun loginAndRefreshTokenTest() {
        val loginRequest = LoginRequest(username = "testuser", password = "password")

        // 1️⃣ 로그인
        val loginResult = mockMvc.perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk)
            .andReturn()

        val loginResponse = objectMapper.readValue(
            loginResult.response.contentAsString,
            ResponseHandler::class.java
        )

        val tokenResponse = objectMapper.convertValue(
            loginResponse.data,
            TokenResponse::class.java
        )

        assertNotNull(tokenResponse.accessToken)
        assertNotNull(tokenResponse.refreshToken)

        // 2️⃣ Refresh Token으로 Access Token 재발급
        val refreshRequest = mapOf("refreshToken" to tokenResponse.refreshToken)

        val refreshResult = mockMvc.perform(
            post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest)))
            .andExpect(status().isOk)
            .andReturn()

        val refreshResponse = objectMapper.readValue(
            refreshResult.response.contentAsString,
            ResponseHandler::class.java
        )

        val newTokenResponse = objectMapper.convertValue(
            refreshResponse.data,
            TokenResponse::class.java
        )

        assertNotNull(newTokenResponse.accessToken)
        assertEquals(tokenResponse.refreshToken, newTokenResponse.refreshToken)
        assertNotEquals(tokenResponse.accessToken, newTokenResponse.accessToken)
    }
}
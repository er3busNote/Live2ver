package com.clone.youtube.domain.auth.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun of(accessToken: String, refreshToken: String): TokenResponse {
            return TokenResponse(accessToken, refreshToken)
        }
    }
}

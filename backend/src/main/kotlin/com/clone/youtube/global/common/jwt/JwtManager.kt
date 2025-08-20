package com.clone.youtube.global.common.jwt

import io.jsonwebtoken.Jwts
import java.util.Date
import javax.crypto.SecretKey

object JwtManager {
    private const val ACCESS_TOKEN_EXPIRATION_MS = 1000 * 60 * 15 // 15분
    private const val REFRESH_TOKEN_EXPIRATION_MS = 1000L * 60 * 60 * 24 * 7 // 7일
    private lateinit var key: SecretKey

    fun init(secretKey: SecretKey) {
        key = secretKey
    }

    private fun getKey(): SecretKey {
        if (!::key.isInitialized) {
            key = Jwts.SIG.HS256.key().build()
        }
        return key
    }

    fun generateAccessToken(username: String): String {
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_MS))
            .signWith(getKey())
            .compact()
    }

    fun generateRefreshToken(username: String): String {
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MS))
            .signWith(getKey())
            .compact()
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }
}
package com.clone.youtube.global.common.jwt

import io.jsonwebtoken.Jwts
import java.util.Date
import javax.crypto.SecretKey

object JwtManager {
    private const val ACCESS_TOKEN_EXPIRATION_MS = 1000 * 60 * 15 // 15분
    private const val REFRESH_TOKEN_EXPIRATION_MS = 1000L * 60 * 60 * 24 * 7 // 7일
    private val key: SecretKey = Jwts.SIG.HS256.key().build()

    fun generateAccessToken(username: String): String {
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_MS))
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(username: String): String {
        return Jwts.builder()
            .subject(username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MS))
            .signWith(key)
            .compact()
    }

    fun getUsernameFromToken(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }
}
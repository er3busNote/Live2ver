package com.live2ver.web.global.common.jwt

import com.live2ver.web.global.common.log.Loggable
import com.live2ver.web.global.common.uuid.UuidManager
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import java.util.Date
import javax.crypto.SecretKey

object JwtManager: Loggable {
    private const val ACCESS_TOKEN_EXPIRATION_MS = 1000 * 60 * 15 // 15분
    private const val REFRESH_TOKEN_EXPIRATION_MS = 1000L * 60 * 60 * 24 * 7 // 7일
    private lateinit var key: SecretKey

    fun init(secretKey: SecretKey) {
        key = secretKey
    }

    private fun getKey(): SecretKey {
        if (!JwtManager::key.isInitialized) {
            key = Jwts.SIG.HS256.key().build()
        }
        return key
    }

    fun generateAccessToken(username: String): String {
        val now = Date()
        val expiry = Date(now.time + ACCESS_TOKEN_EXPIRATION_MS)
        return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(expiry)
            .claim("jti", UuidManager.generateUuid())
            .signWith(getKey())
            .compact()
    }

    fun generateRefreshToken(username: String): String {
        val now = Date()
        val expiry = Date(now.time + REFRESH_TOKEN_EXPIRATION_MS)
        return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(getKey())
            .compact()
    }

    fun validate(token: String): Boolean {
        return try {
            this.validateAndGetClaims(token)
            true
        } catch (e: SecurityException) {
            log.info("Invalid JWT signature: ${e.message}")
            false
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT token: ${e.message}")
            false
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT token: ${e.message}")
            false
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT token: ${e.message}")
            false
        } catch (e: IllegalArgumentException) {
            log.info("JWT claims string is empty or invalid: ${e.message}")
            false
        }
    }

    fun validateAndGetClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(getKey())
            .build()

        val jwt: Jwt<JwsHeader, Claims> = parser.parseSignedClaims(token)
        val claims = jwt.payload

        val expiration = claims.expiration
        if (expiration != null && expiration.before(Date())) {
            throw ExpiredJwtException(null, claims, "Expired JWT token")
        }

        return claims
    }
}
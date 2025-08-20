package com.clone.youtube.global.configs

import com.clone.youtube.global.common.properties.JwtProperties
import io.jsonwebtoken.security.Keys
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@Configuration
class JwtConfig(
    private val jwtProperties: JwtProperties
) {
    @Bean
    fun jwtSecretKey(): SecretKey {
        return Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
    }
}
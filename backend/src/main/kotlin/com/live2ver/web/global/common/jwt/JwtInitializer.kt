package com.live2ver.web.global.common.jwt

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class JwtInitializer(private val key: SecretKey) {
    @PostConstruct
    fun init() {
        JwtManager.init(key)
    }
}
package com.live2ver.web.global.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
class JwtProperties {
    lateinit var secret: String
}
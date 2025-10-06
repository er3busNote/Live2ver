package com.live2ver.web.global.common.uuid

import org.springframework.stereotype.Component
import java.util.UUID

@Component
object UuidManager {
    fun generateId(): String =
        UUID.randomUUID().toString().replace("-", "").uppercase()

    fun generateUuid(): String =
        UUID.randomUUID().toString()
}
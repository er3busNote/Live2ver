package com.live2ver.web.global.common.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Loggable {
    val log: Logger
        get() = LoggerFactory.getLogger(this::class.java)
}
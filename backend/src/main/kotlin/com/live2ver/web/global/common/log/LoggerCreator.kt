package com.live2ver.web.global.common.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class LoggerCreator {
    val log: Logger by lazy {
        LoggerFactory.getLogger(this.javaClass.enclosingClass)
    }
}
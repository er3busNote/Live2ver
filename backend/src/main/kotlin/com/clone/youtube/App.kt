package com.clone.youtube

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YoutubeApplication

fun main(args: Array<String>) {
	runApplication<YoutubeApplication>(*args)
}

package com.behealthy.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    private val logger = mu.KotlinLogging.logger {}

    @GetMapping("/ping")
    fun pong(): String {
        logger.info { "ping" }
        return "success"
    }
}
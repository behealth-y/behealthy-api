package com.behealthy

import com.behealthy.config.Env
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
        setDefaultProperties(hashMapOf("spring.profiles.default" to Env.DEV) as Map<String, *>)
    }
}

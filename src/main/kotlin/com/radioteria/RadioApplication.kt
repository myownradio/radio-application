package com.radioteria

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("classpath:radioteria.properties")
class RadioApplication

fun main(args: Array<String>) {
    val webPort = System.getenv("PORT")

    if (webPort != null && webPort.isNotEmpty()) {
        System.setProperty("server.port", webPort);
    }

    SpringApplication.run(RadioApplication::class.java, *args)
}
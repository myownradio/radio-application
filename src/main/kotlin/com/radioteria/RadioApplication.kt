package com.radioteria

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
class RadioApplication

fun main(args: Array<String>) {
    SpringApplication.run(RadioApplication::class.java, *args)
}

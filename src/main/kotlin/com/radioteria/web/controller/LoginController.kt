package com.radioteria.web.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {
    @RequestMapping(value = "/api/auth/login")
    fun login() {

    }
}
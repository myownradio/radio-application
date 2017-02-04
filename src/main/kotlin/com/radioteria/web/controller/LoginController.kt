package com.radioteria.web.controller

import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {
    @RequestMapping(value = "/api/auth/login")
    fun login() {

    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/foo", method = arrayOf(RequestMethod.POST))
    fun foo() {

    }
}
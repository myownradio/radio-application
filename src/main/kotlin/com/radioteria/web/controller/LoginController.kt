package com.radioteria.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {
    @Secured("ROLE_USER", "ROLE_ADMIN")
    @RequestMapping(value = "/foo")
    fun foo() {

    }

    @RequestMapping("/me")
    fun me(): ResponseEntity<Authentication> {
        return ResponseEntity.ok(SecurityContextHolder.getContext().authentication)
    }
}
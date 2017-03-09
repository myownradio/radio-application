package com.radioteria.web.controller

import com.radioteria.service.auth.RegistrationService
import com.radioteria.web.request.RegistrationRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth/register")
class RegistrationController(val registrationService: RegistrationService) {

    @PostMapping
    fun register(@RequestBody req: RegistrationRequest): RegistrationRequest {
        return req
    }

}
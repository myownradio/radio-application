package com.radioteria.web.request

import com.radioteria.web.request.annotation.AvailableEmail
import javax.validation.constraints.Size

data class RegistrationRequest(
        @AvailableEmail
        @Size(min = 1, message = "Email is too short")
        val email: String,

        @Size(min = 1, message = "Password is too short")
        val password: String,

        val name: String
)
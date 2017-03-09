package com.radioteria.service.auth

import com.radioteria.domain.entity.User

interface RegistrationService {
    fun register(email: String, password: String, name: String): User
}
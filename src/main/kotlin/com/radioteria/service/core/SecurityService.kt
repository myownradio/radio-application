package com.radioteria.service.core

import com.radioteria.domain.entity.User

interface SecurityService {
    fun getAuthenticatedUser(): User?
    fun isAuthenticated(): Boolean
}

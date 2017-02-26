package com.radioteria.service.core

import com.radioteria.domain.entity.User

interface SecurityService {
    fun getLoggedInUser(): User?
    fun isAuthenticated(): Boolean
}

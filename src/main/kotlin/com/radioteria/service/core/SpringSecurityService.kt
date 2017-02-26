package com.radioteria.service.core

import com.radioteria.auth.UserEntityDetails
import com.radioteria.domain.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SpringSecurityService : SecurityService {

    override fun getLoggedInUser(): User? {
        return (SecurityContextHolder.getContext()
                ?.authentication
                ?.principal as? UserEntityDetails)
                ?.user
    }

    override fun isAuthenticated(): Boolean {
        return getLoggedInUser() != null
    }

}
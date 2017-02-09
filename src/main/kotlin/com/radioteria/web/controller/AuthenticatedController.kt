package com.radioteria.web.controller

import com.radioteria.auth.UserEntityDetails
import com.radioteria.domain.entity.User
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder

@Secured("ROLE_USER")
abstract class AuthenticatedController {
    protected val authenticatedUser: User
        get() = (SecurityContextHolder.getContext().authentication.principal as UserEntityDetails).user
}
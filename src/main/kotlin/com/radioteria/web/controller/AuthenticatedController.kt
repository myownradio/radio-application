package com.radioteria.web.controller

import com.radioteria.auth.BelongsToUser
import com.radioteria.auth.NoPermissionException
import com.radioteria.auth.UserEntityDetails
import com.radioteria.domain.entity.User
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder

@Secured("ROLE_USER")
abstract class AuthenticatedController {
    fun getAuthenticatedUser(): User {
        val securityContext = SecurityContextHolder.getContext()
        val authentication = securityContext.authentication
        val userDetails = authentication.principal as UserEntityDetails

        return userDetails.user
    }

    fun isBelongsToMe(entity: BelongsToUser): Boolean {
        return entity.belongsTo(getAuthenticatedUser())
    }

    fun denyAccessIfNotMine(entity: BelongsToUser) {
        if (!isBelongsToMe(entity)) {
            throwAccessDenied()
        }
    }

    fun throwAccessDenied() {
        throw NoPermissionException("You do not have permissions to access this resource.")
    }
}
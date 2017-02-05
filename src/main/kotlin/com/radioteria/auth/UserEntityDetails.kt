package com.radioteria.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

import com.radioteria.domain.entity.User as UserEntity

class UserEntityDetails(val user: com.radioteria.domain.entity.User)
    : User(user.email, user.password, userRoleToAuthorities(user.role)) {

    companion object {
        val roleMap = mapOf(
                UserEntity.Role.USER  to "ROLE_USER",
                UserEntity.Role.ADMIN to "ROLE_ADMIN"
        )

        private fun userRoleToAuthorities(role: UserEntity.Role): Set<GrantedAuthority> {
            return setOf(SimpleGrantedAuthority(roleMap[role]))
        }
    }
}

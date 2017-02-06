package com.radioteria.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import com.radioteria.domain.entity.User

class UserEntityDetails(val user: User) : UserDetails {

    companion object {
        val rolePrefix = "ROLE_"
    }

    override fun getUsername(): String = user.email

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = user.status == User.Status.ACTIVE

    override fun getAuthorities(): Set<GrantedAuthority> {
        return setOf(SimpleGrantedAuthority(rolePrefix + user.role))
    }

    override fun isEnabled(): Boolean = user.status == User.Status.ACTIVE

    override fun getPassword(): String = user.password

}

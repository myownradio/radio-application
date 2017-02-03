package com.radioteria.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class AuthenticationConfig(private val userDetailsService: UserDetailsService)
    : GlobalAuthenticationConfigurerAdapter() {

    override fun init(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }
}
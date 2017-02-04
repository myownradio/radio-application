package com.radioteria.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter

@Configuration
class GlobalSecurityConfig : GlobalAuthenticationConfigurerAdapter() {
    override fun init(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
    }
}
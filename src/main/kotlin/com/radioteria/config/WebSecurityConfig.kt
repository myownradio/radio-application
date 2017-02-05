package com.radioteria.config

import com.radioteria.auth.UserEntityDetails
import com.radioteria.domain.repository.UserRepository
import com.radioteria.web.sendOk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED

import com.radioteria.domain.entity.User as UserEntity

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun configure(http: HttpSecurity) {
        http.csrf()
                .disable()

        http.httpBasic()
                .disable()

        http.exceptionHandling()
                .authenticationEntryPoint { request, response, exception ->
                    response.sendError(SC_UNAUTHORIZED, exception.message)
                }

        http.formLogin()
                .usernameParameter("email")
                .loginProcessingUrl("/api/auth/login")
                .successHandler { request, response, authentication -> response.sendOk() }
                .failureHandler { request, response, exception ->
                    response.sendError(SC_UNAUTHORIZED, exception.message)
                }

        http.logout()
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler { request, response, authentication -> response.sendOk() }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService())
    }

    override fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            val user = userRepository.findByEmail(username) ?:
                    throw UsernameNotFoundException("User with email \"$username\" does not exist.")

            UserEntityDetails(user)
        }
    }
}
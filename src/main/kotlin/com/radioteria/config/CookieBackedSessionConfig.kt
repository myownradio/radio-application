package com.radioteria.config

import com.radioteria.config.spring.session.CookieBackedSessionRepository
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class CookieBackedSessionConfig(private val applicationContext: ApplicationContext) {
    @Bean
    fun sessionRepository(): CookieBackedSessionRepository {
        return object : CookieBackedSessionRepository() {
            override fun getResponse(): HttpServletResponse {
                return applicationContext.getBean(HttpServletResponse::class.java)
            }

            override fun getRequest(): HttpServletRequest {
                return applicationContext.getBean(HttpServletRequest::class.java)
            }
        }
    }
}
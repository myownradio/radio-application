package com.radioteria.bpp

import com.radioteria.annotation.InjectUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils

@Component
class InjectUserDetailsAnnotationBeanPostProcessor : BeanPostProcessor {
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        for (field in bean.javaClass.fields) {
            val annotation = field.getAnnotation(InjectUserDetails::class.java)
            if (annotation != null) {
                val email = annotation.value
                val userDetails = userDetailsService.loadUserByUsername(email)
                field.isAccessible = true
                ReflectionUtils.setField(field, bean, userDetails)
            }
        }

        return bean
    }
}
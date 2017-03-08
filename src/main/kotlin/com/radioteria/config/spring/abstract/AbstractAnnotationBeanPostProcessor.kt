package com.radioteria.config.spring.abstract

import org.springframework.beans.PropertyValues
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor
import org.springframework.stereotype.Component
import java.beans.PropertyDescriptor

@Component
class AbstractAnnotationBeanPostProcessor : InstantiationAwareBeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        return bean
    }

    override fun postProcessAfterInstantiation(bean: Any, beanName: String): Boolean {
        return true
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        return bean
    }

    override fun postProcessBeforeInstantiation(beanClass: Class<*>, beanName: String): Any? {
        System.err.println("Create: $beanClass")
        return null
    }

    override fun postProcessPropertyValues(pvs: PropertyValues, pds: Array<out PropertyDescriptor>, bean: Any, beanName: String): PropertyValues {
        return pvs
    }

}
package com.radioteria.config.spring.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.lang.reflect.Proxy

@Component
class LoggingAnnotationBeanPostProcessor : BeanPostProcessor {

    val beans: MutableMap<String, Class<Any>> = mutableMapOf()

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        val originalClass = bean.javaClass
        val annotation = originalClass.getAnnotation(Logging::class.java)

        if (annotation != null) {
            beans.put(beanName, originalClass)
        }

        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        val originalClass = beans[beanName]

        if (originalClass != null) {
            val logger: Logger = LoggerFactory.getLogger(originalClass)

            return Proxy.newProxyInstance(originalClass.classLoader, originalClass.interfaces) { proxy, method, args ->
                val timeBefore = System.currentTimeMillis()
                val returnValue = method.invoke(bean, *args)
                val timeAfter = System.currentTimeMillis()
                val message = renderMethodCallMessage(method, args, returnValue, timeAfter - timeBefore)

                logger.info(message)

                returnValue
            }
        }

        return bean
    }

    private fun renderMethodCallMessage(method: Method, args: Array<Any?>, returnValue: Any?, time: Long): String {
        val methodName = method.name
        val argumentsString = args.map { toWrappedString(it) }.joinToString(", ")
        val returnValueAsString = toWrappedString(returnValue)

        return "Called method '$methodName($argumentsString)': [time=$time, return=$returnValueAsString]"
    }

    private fun toWrappedString(obj: Any?): String {
        if (obj is String) {
            return "\"$obj\""
        }
        return obj.toString()
    }

}
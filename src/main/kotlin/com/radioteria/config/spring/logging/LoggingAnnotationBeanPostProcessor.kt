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

        if (originalClass.isAnnotationPresent(Logging::class.java)) {
            beans.put(beanName, originalClass)
        }

        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        val originalClass = beans[beanName]

        if (originalClass != null) {
            val logger: Logger = LoggerFactory.getLogger(originalClass)

            return Proxy.newProxyInstance(originalClass.classLoader, originalClass.interfaces) { proxy, method, args ->
                val result = method.invoke(bean, *args)
                val message = renderMethodCallMessage(method, args, result)
                logger.info(message)
                result
            }
        }

        return bean
    }

    private fun renderMethodCallMessage(method: Method, args: Array<Any>, result: Any): String {
        val methodName = method.name
        val argumentsString = args.map(Any::toString).joinToString(", ")
        val resultString = result.toString()

        return "Method Call: $methodName($argumentsString) -> $resultString"
    }

}
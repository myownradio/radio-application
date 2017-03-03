package com.radioteria.service.core

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

@ConditionalOnProperty("radioteria.time.service", havingValue = "static")
@Service
class StaticTimeService(@Value("\${radioteria.time.static}") private val staticTime: Long) : TimeService {
    override fun getTime(): Long = staticTime
}
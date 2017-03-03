package com.radioteria.service.core

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

@ConditionalOnProperty("radioteria.time.service", havingValue = "static")
@Service
class StaticTimeService : TimeService {
    val currentTime = System.currentTimeMillis()
    override fun getTime(): Long = currentTime
}
package com.radioteria.service.core

import org.springframework.stereotype.Service

@Service
class StaticTimeService : TimeService {
    val currentTime = System.currentTimeMillis()
    override fun getTime(): Long = currentTime
}
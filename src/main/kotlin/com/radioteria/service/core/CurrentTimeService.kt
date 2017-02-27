package com.radioteria.service.core

import org.springframework.stereotype.Service

@Service
class CurrentTimeService : TimeService {
    override fun getTime(): Long {
        return System.currentTimeMillis()
    }
}

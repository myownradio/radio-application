package com.radioteria.domain.service

import com.radioteria.domain.entity.Channel

interface ChannelStateService {
    fun expectIsStarted(channel: Channel)
    fun expectIsNotEmpty(channel: Channel)
    fun expectIsStopped(channel: Channel)
}
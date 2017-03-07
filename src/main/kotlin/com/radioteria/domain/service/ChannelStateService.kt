package com.radioteria.domain.service

import com.radioteria.domain.entity.Channel

interface ChannelStateService {
    fun failIfStopped(channel: Channel)
    fun failIfEmpty(channel: Channel)
    fun failIfStarted(channel: Channel)
}
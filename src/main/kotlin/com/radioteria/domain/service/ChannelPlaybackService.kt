package com.radioteria.domain.service

import com.radioteria.domain.entity.Channel

interface ChannelPlaybackService {
    fun playFromStart(channel: Channel)
    fun playFromPosition(channel: Channel, position: Long)
    fun rewind(channel: Channel, amount: Long)
    fun stop(channel: Channel)
}
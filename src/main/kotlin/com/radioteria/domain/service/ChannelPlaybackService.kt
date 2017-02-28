package com.radioteria.domain.service

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track

interface ChannelPlaybackService {
    fun start(channel: Channel)
    fun stop(channel: Channel)
    fun seek(channel: Channel, amount: Long)

    fun startFromOffset(channel: Channel, offset: Long)
}
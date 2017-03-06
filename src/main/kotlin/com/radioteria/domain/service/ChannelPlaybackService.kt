package com.radioteria.domain.service

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track

interface ChannelPlaybackService {
    fun startChannel(channel: Channel)
    fun startChannelFromTimePosition(channel: Channel, timePosition: Long)
    fun stopChannel(channel: Channel)
    fun seekChannel(channel: Channel, amount: Long)
}
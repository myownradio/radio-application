package com.radioteria.domain.service.impl

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.service.ChannelPlaybackService
import org.springframework.stereotype.Service

@Service
class ChannelPlaybackServiceImpl : ChannelPlaybackService {

    override fun start(channel: Channel) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun stop(channel: Channel) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun seek(channel: Channel, amount: Long) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun startFromOffset(channel: Channel, offset: Long) {
        throw UnsupportedOperationException("not implemented")
    }

}
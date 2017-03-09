package com.radioteria.domain.service.impl

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.domain.service.ChannelStateService
import com.radioteria.domain.service.exception.ChannelStateException
import org.springframework.stereotype.Service

@Service
class ChannelStateServiceImpl(val trackRepository: TrackRepository) : ChannelStateService {

    override fun expectIsStarted(channel: Channel) {
        if (!channel.isStarted()) {
            throw ChannelStateException("Channel $channel is not started.")
        }
    }

    override fun expectIsNotEmpty(channel: Channel) {
        if (0L == trackRepository.getTracklistDurationByChannelId(channel.id)) {
            throw ChannelStateException("Channel $channel has no tracks.")
        }
    }

    override fun expectIsStopped(channel: Channel) {
        if (channel.isStarted()) {
            throw ChannelStateException("Channel $channel is not stopped.")
        }
    }

}
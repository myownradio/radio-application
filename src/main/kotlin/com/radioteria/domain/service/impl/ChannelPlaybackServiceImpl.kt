package com.radioteria.domain.service.impl

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.domain.service.ChannelPlaybackService
import com.radioteria.service.core.TimeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelPlaybackServiceImpl(
        val timeService: TimeService,
        val channelRepository: ChannelRepository
) : ChannelPlaybackService {

    @Transactional
    override fun startChannel(channel: Channel) {
        startChannelFromTimePosition(channel, 0)
    }

    @Transactional
    override fun startChannelFromTimePosition(channel: Channel, timePosition: Long) {
        if (channel.isStarted()) {
            return
        }
        channel.startedAt = timeService.getTime() - timePosition
        channelRepository.save(channel)
    }

    @Transactional
    override fun stopChannel(channel: Channel) {
        if (!channel.isStarted()) {
            return
        }
        channel.startedAt = null
        channelRepository.save(channel)
    }

    @Transactional
    override fun seekChannel(channel: Channel, amount: Long) {
        if (!channel.isStarted()) {
            return
        }
        channelRepository.increaseStartedAt(channel.id, amount)
        refreshChannelStartedAt(channel)
    }

    private fun refreshChannelStartedAt(channel: Channel) {
        channelRepository.findOne(channel.id)
                ?.let { channel.startedAt = it.startedAt }
    }

}
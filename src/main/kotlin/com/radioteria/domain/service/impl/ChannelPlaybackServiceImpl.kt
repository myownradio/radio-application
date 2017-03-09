package com.radioteria.domain.service.impl

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.domain.service.ChannelPlaybackService
import com.radioteria.domain.service.ChannelStateService
import com.radioteria.domain.service.NowPlayingService
import com.radioteria.domain.service.event.ChannelRewindTrackEvent
import com.radioteria.domain.service.event.ChannelSkipTrackEvent
import com.radioteria.domain.service.event.ChannelStartedEvent
import com.radioteria.domain.service.event.ChannelStoppedEvent
import com.radioteria.service.core.TimeService
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ChannelPlaybackServiceImpl(
        val timeService: TimeService,
        val channelRepository: ChannelRepository,
        val nowPlayingService: NowPlayingService,
        val channelStateService: ChannelStateService,
        val eventMulticaster: ApplicationEventMulticaster
) : ChannelPlaybackService {

    override fun startChannel(channel: Channel) {
        startChannelFromTimePosition(channel, 0)
    }

    override fun startChannelFromTimePosition(channel: Channel, timePosition: Long) {
        channelStateService.expectIsStopped(channel)
        channelStateService.expectNotEmpty(channel)
        channel.startedAt = timeService.getTime() - timePosition
        channelRepository.save(channel)

        eventMulticaster.multicastEvent(ChannelStartedEvent(this, channel))
    }

    override fun stopChannel(channel: Channel) {
        channelStateService.expectIsStarted(channel)
        channel.startedAt = null
        channelRepository.save(channel)

        eventMulticaster.multicastEvent(ChannelStoppedEvent(this, channel))
    }

    override fun seekChannel(channel: Channel, amount: Long) {
        channelStateService.expectIsStarted(channel)
        channelRepository.increaseStartedAt(channel.id, -amount)
        refreshChannelStartedAt(channel)
    }

    override fun skipTrackOnChannel(channel: Channel) {
        val nowPlaying = nowPlayingService.getNowPlaying(channel)
        val seekAmount = nowPlaying.track.duration - nowPlaying.timePosition
        seekChannel(channel, seekAmount)

        eventMulticaster.multicastEvent(ChannelSkipTrackEvent(this, channel))
    }

    override fun rewindTrackOnChannel(channel: Channel) {
        val nowPlaying = nowPlayingService.getNowPlaying(channel)
        seekChannel(channel, -nowPlaying.timePosition)

        eventMulticaster.multicastEvent(ChannelRewindTrackEvent(this, channel))
    }

    private fun refreshChannelStartedAt(channel: Channel) {
        channelRepository.findOne(channel.id)
                ?.let { channel.startedAt = it.startedAt }
    }

}
package com.radioteria.domain.service.impl

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.domain.service.ChannelStateService
import com.radioteria.domain.service.NowPlayingService
import com.radioteria.service.core.TimeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class NowPlayingServiceImpl(
        val trackRepository: TrackRepository,
        val timeService: TimeService,
        val channelStateService: ChannelStateService
) : NowPlayingService {

    override fun getNowPlaying(channel: Channel): NowPlayingService.NowPlaying {
        val channelLapTime = getChannelLapTime(channel)

        val track = trackRepository.findOneByChannelIdAndLapPosition(channel.id, channelLapTime) ?:
                throw IllegalStateException("Could not determine what is playing at $channelLapTime second(s) on channel $channel.")

        val timePosition = channelLapTime - track.offset

        return NowPlayingService.NowPlaying(track, timePosition)
    }

    private fun getChannelLapTime(channel: Channel): Long {
        val channelUptime = getChannelUptime(channel)

        channelStateService.expectIsNotEmpty(channel)

        val tracklistDuration = trackRepository.getTracklistDurationByChannelId(channel.id)

        return channelUptime % tracklistDuration
    }

    private fun getChannelUptime(channel: Channel): Long {
        channelStateService.expectIsStarted(channel)

        return timeService.getTime() - channel.startedAt!!
    }

}
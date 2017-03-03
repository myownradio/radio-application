package com.radioteria.domain.service.impl

import com.peacefulbit.util.unless
import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.domain.service.NowPlayingService
import com.radioteria.service.core.TimeService
import org.springframework.stereotype.Service

@Service
class NowPlayingServiceImpl(val trackRepository: TrackRepository, val timeService: TimeService) : NowPlayingService {

    override fun getNowPlaying(channel: Channel): NowPlayingService.NowPlaying {
        val channelLapTime = getChannelLapTime(channel)

        val track = trackRepository.findOneByChannelIdAndLapPosition(channel.id, channelLapTime) ?:
                throw IllegalStateException("Could not determine what is playing at $channelLapTime second(s) on channel $channel.")

        val timePosition = channelLapTime - track.offset

        return NowPlayingService.NowPlaying(track, timePosition)
    }

    private fun getChannelLapTime(channel: Channel): Long {
        val channelUptime = getChannelUptime(channel)
        val tracklistDuration = trackRepository.getTracklistDurationByChannelId(channel.id)

        if (tracklistDuration == 0L) {
            throw IllegalStateException("Channel $channel has no tracks.")
        }

        return channelUptime % tracklistDuration
    }

    private fun getChannelUptime(channel: Channel): Long {
        unless (channel.isStarted()) {
            throw IllegalStateException("Channel $channel is not started.")
        }

        return timeService.getTime() - channel.startedAt!!
    }

}
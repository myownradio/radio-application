package com.radioteria.domain.service.impl

import com.peacefulbit.util.unless
import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.domain.service.NowPlayingService
import com.radioteria.service.core.TimeService
import org.springframework.stereotype.Service

@Service
class NowPlayingServiceImpl(val timeService: TimeService, val trackRepository: TrackRepository) : NowPlayingService {

    override fun getNowPlaying(channel: Channel): NowPlayingService.NowPlaying {
        val channelLapTime = getChannelLapTime(channel)

        trackRepository.findOneByChannelIdAndLapTime(channel.id, channelLapTime)

        trackRepository.findAllByChannelIdOrderByOffsetAsc(channel.id).forEach { track ->
            if (track.offset <= channelLapTime && track.offset + track.duration > channelLapTime) {
                val timePosition = channelLapTime - track.offset
                return NowPlayingService.NowPlaying(track, timePosition)
            }
        }

        throw IllegalStateException("Could not determine what is playing on channel $channel.")
    }

    override fun getChannelLapTime(channel: Channel): Long {
        val tracklistDuration = trackRepository.getTracklistDurationByChannelId(channel.id)
        if (tracklistDuration == 0L) {
            throw IllegalStateException("Channel has no tracks.")
        }
        return getChannelUptime(channel) % tracklistDuration
    }

    override fun getChannelUptime(channel: Channel): Long {
        unless (channel.isStarted()) {
            throw IllegalStateException("Channel $channel is not started.")
        }
        return timeService.getTime() - channel.startedAt!!
    }

}
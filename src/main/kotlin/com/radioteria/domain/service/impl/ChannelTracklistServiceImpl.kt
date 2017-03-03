package com.radioteria.domain.service.impl

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import com.radioteria.domain.ids
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.domain.service.ChannelPlaybackService
import com.radioteria.domain.service.ChannelTracklistService
import com.radioteria.domain.service.NowPlayingService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ChannelTracklistServiceImpl(
        val trackRepository: TrackRepository,
        val channelPlaybackService: ChannelPlaybackService,
        val nowPlayingService: NowPlayingService
) : ChannelTracklistService {

    @Transactional
    override fun add(track: Track) {
        val channel = track.channel
        val nextOffset = trackRepository.getTracklistDurationByChannelId(channel.id)

        doSynchronized(channel) {
            track.offset = nextOffset
            trackRepository.save(track)
        }
    }

    @Transactional
    override fun delete(track: Track) {
        val channel = track.channel
        val tracksToUpdate = trackRepository.findAllByChannelIdAndOffsetGreaterThan(channel.id, track.offset)

        doSynchronized(channel) {
            trackRepository.increaseOffsetWhereIdIn(tracksToUpdate.ids(), -track.duration)
            trackRepository.delete(track)
        }
    }

    @Transactional
    override fun move(track: Track, index: Int) {
        val channel = track.channel

        doSynchronized(channel) {
            TODO("not implemented")
        }
    }

    private fun doSynchronized(channel: Channel, block: () -> Unit) {
        if (!channel.isStarted()) {
            return block.invoke()
        }

        val nowPlaying = nowPlayingService.getNowPlaying(channel)

        block.invoke()

        val updatedOffset = trackRepository.findOne(nowPlaying.track.id)?.offset
                ?: return channelPlaybackService.seekChannel(channel, -nowPlaying.timePosition) // todo: May be buggy on bulk deletion

        val rewindAmount = updatedOffset - nowPlaying.track.offset

        channelPlaybackService.seekChannel(channel, rewindAmount)
    }

}
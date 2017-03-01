package com.radioteria.domain.service.impl

import com.radioteria.domain.entity.Track
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

        val newTrackOffset = trackRepository.getTracklistDurationByChannelId(channel.id)

        doSynchronized(track) { track ->
            track.offset = newTrackOffset
            trackRepository.save(track)
        }
    }

    @Transactional
    override fun delete(track: Track) {
        val channel = track.channel

        doSynchronized(track) { track ->
            trackRepository.delete(track)
            trackRepository.moveOffsetsByAmountAfterGiven(channel.id, track.duration, track.offset)
        }
    }

    @Transactional
    override fun move(track: Track, newPosition: Int) {
        val channel = track.channel
        val tracklist = trackRepository.findAllByChannelIdOrderByOffsetAsc(channel.id)

        val targetTrack = tracklist[newPosition.dec()]

        doSynchronized(track) { track ->
//            trackRepository.moveOffsetsByAmountBetweenGiven(channel.id, track.duration, oneBound, secondBound)
        }
    }

    private fun doSynchronized(track: Track, block: (Track) -> Unit) {
        val channel = track.channel

        if (!channel.isStarted()) {
            block.invoke(track)
            return
        }

        val nowPlaying = nowPlayingService.getNowPlaying(channel)

        block.invoke(track)

        val lastPlayingTrack = trackRepository.findOne(nowPlaying.track.id)

        if (lastPlayingTrack == null) {
            channelPlaybackService.startChannelFromTimePosition(channel, nowPlaying.track.offset)
            return
        }

        val rewindAmount = lastPlayingTrack.offset - nowPlaying.track.offset

        channelPlaybackService.seekChannel(channel, rewindAmount)
    }

}
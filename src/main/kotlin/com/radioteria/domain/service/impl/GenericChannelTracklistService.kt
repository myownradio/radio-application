package com.radioteria.domain.service.impl

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.domain.service.ChannelPlaybackService
import com.radioteria.service.core.TimeService
import com.radioteria.domain.service.ChannelTracklistService
import java.util.concurrent.atomic.AtomicLong

class GenericChannelTracklistService(
        val channelRepository: ChannelRepository,
        val trackRepository: TrackRepository,
        val channelPlaybackService: ChannelPlaybackService,
        val timeService: TimeService
) : ChannelTracklistService {

    override fun add(track: Track) {
        val tracksInChannel = trackRepository
                .findAllByChannelIdOrderByPositionAsc(track.channel.id)
        val nextPosition = tracksInChannel.size.toLong()

        doSynchronized(track) { track ->
            track.position = nextPosition
            trackRepository.save(track)
        }
    }

    override fun delete(track: Track) {

    }

    override fun move(track: Track, newPosition: Long) {

    }

    private fun doSynchronized(track: Track, block: (Track) -> Unit) {
        val channel = track.channel

        if (!channel.isStarted()) {
            block.invoke(track)
            return
        }

        val (currentTrackBeforeAction, currentTrackOffsetBeforeAction) = getNowPlayingOnChannel(channel)!!

        block.invoke(track)

        val tracklistWithOffsetsAfterAction = getTracklistWithOffsetsByChannel(channel)
        val currentTrackOffsetAfterAction = tracklistWithOffsetsAfterAction
                .find { it.first.id == currentTrackBeforeAction.id }

        if (currentTrackOffsetAfterAction == null) {
            channelPlaybackService.playFromPosition(channel, currentTrackBeforeAction.position)
            return
        }

        val rewindAmount = currentTrackOffsetAfterAction.second - currentTrackOffsetBeforeAction

        channelPlaybackService.rewind(channel, rewindAmount)
    }

    private fun (List<Track>).withOffsets(): List<Pair<Track, Long>> {
        val offset = AtomicLong(0L)
        return map { Pair(it, offset.getAndAdd(it.duration)) }
    }

    private fun getNowPlayingOnChannel(channel: Channel): Pair<Track, Long>? {
        val tracklist = getChannelTracklist(channel)
        val tracklistWithOffsets = tracklist.withOffsets()
        return getChannelTracklistLapTime(channel, tracklist)?.let { lapTime ->
            tracklistWithOffsets
                    .find { lapTime >= it.second && lapTime < it.second + it.first.duration }
        }
    }

    private fun getTracklistWithOffsetsByChannel(channel: Channel): List<Pair<Track, Long>> {
        return getChannelTracklist(channel)
                .withOffsets()
    }

    private fun getChannelTracklist(channel: Channel): List<Track> {
        return trackRepository
                .findAllByChannelIdOrderByPositionAsc(channel.id)
    }

    private fun getTracklistDuration(tracklist: List<Track>): Long {
        return tracklist.map { it.duration }.sum()
    }

    private fun getChannelTracklistLapTime(channel: Channel, tracklist: List<Track>): Long? {
        return getChannelTracklistUptime(channel)?.let { it % getTracklistDuration(tracklist) }
    }

    private fun getChannelTracklistUptime(channel: Channel): Long? {
        return channel.startedAt?.let { timeService.getTime() - it }
    }

}
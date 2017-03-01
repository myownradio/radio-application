package com.radioteria.service

import com.radioteria.annotation.DatabaseTest
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.domain.service.NowPlayingService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
@DatabaseTest
class NowPlayingServiceTest {

    val startedChannelId = 6L
    val stoppedChannelId = 7L
    val withoutTracksChannelId = 8L

    @Autowired
    lateinit var nowPlayingService: NowPlayingService

    @Autowired
    lateinit var channelRepository: ChannelRepository

    @Test
    fun whenChannelHasTracksAndStarted() {
        val channel = channelRepository.findOne(startedChannelId)!!
        val nowPlaying = nowPlayingService.getNowPlaying(channel)

        System.err.println(nowPlaying)
    }

    @Test(expected = IllegalStateException::class)
    fun whenChannelIsStopped() {
        val channel = channelRepository.findOne(stoppedChannelId)!!
        val nowPlaying = nowPlayingService.getNowPlaying(channel)
    }

    @Test(expected = IllegalStateException::class)
    fun whenChannelHaveNoTracks() {
        val channel = channelRepository.findOne(withoutTracksChannelId)!!
        val nowPlaying = nowPlayingService.getNowPlaying(channel)
    }

}
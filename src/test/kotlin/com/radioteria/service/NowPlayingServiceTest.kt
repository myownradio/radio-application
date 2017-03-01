package com.radioteria.service

import com.radioteria.annotation.DatabaseTest
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.domain.service.NowPlayingService
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
@DatabaseTest
class NowPlayingServiceTest {

    companion object {
        val STARTED_CHANNEL_ID = 6L
        val STOPPED_CHANNEL_ID = 7L
        val WITHOUT_TRACKS_CHANNEL_ID = 8L
    }

    @Autowired
    lateinit var nowPlayingService: NowPlayingService

    @Autowired
    lateinit var channelRepository: ChannelRepository

    @Test
    fun whenChannelHaveTracksAndStarted() {
        val channel = channelRepository.findOne(STARTED_CHANNEL_ID)!!
        val nowPlaying = nowPlayingService.getNowPlaying(channel)

        assertThat(nowPlaying, instanceOf(NowPlayingService.NowPlaying::class.java))
    }

    @Test(expected = IllegalStateException::class)
    fun whenChannelIsStopped() {
        val channel = channelRepository.findOne(STOPPED_CHANNEL_ID)!!
        nowPlayingService.getNowPlaying(channel)
    }

    @Test(expected = IllegalStateException::class)
    fun whenChannelHaveNoTracks() {
        val channel = channelRepository.findOne(WITHOUT_TRACKS_CHANNEL_ID)!!
        nowPlayingService.getNowPlaying(channel)
    }

}
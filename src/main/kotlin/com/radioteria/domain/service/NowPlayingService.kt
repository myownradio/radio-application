package com.radioteria.domain.service

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track

interface NowPlayingService {
    data class NowPlaying(val track: Track, val timePosition: Long)

    fun getNowPlaying(channel: Channel): NowPlaying
}
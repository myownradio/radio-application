package com.radioteria.domain.service

import com.radioteria.domain.entity.Track

interface ChannelTracklistService {
    fun add(track: Track)
    fun delete(track: Track)
    fun move(track: Track, newPosition: Int)
}
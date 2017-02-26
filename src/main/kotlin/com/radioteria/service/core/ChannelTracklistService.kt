package com.radioteria.service.core

import com.radioteria.domain.entity.Track

interface ChannelTracklistService {
    fun add(track: Track)
    fun delete(track: Track)
    fun move(track: Track, newPosition: Long)
}
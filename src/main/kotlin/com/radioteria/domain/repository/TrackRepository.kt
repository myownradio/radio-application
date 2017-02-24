package com.radioteria.domain.repository

import com.radioteria.domain.entity.Track
import org.springframework.data.repository.Repository

interface TrackRepository : Repository<Track, Long> {
    fun findOne(id: Long?): Track?
    fun save(track: Track)
    fun delete(track: Track)
}
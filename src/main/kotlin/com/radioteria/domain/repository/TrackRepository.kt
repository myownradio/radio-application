package com.radioteria.domain.repository

import com.radioteria.domain.entity.Track
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface TrackRepository : Repository<Track, Long> {
    fun findOne(id: Long?): Track?
    fun findAllByChannelIdOrderByOffsetAsc(channelId: Long?): List<Track>

    fun save(track: Track)
    fun delete(track: Track)

    @Query("SELECT SUM(t.duration) FROM Track t WHERE t.channel.id = ?1")
    fun getTracklistDurationByChannelId(id: Long?): Long

    @Query("FROM Track t WHERE t.offset >= ?2 AND t.offset + t.duration > ?2 AND t.channel.id = ?1 ORDER BY t.offset DESC")
    fun findOneByChannelIdAndLapTime(channelId: Long?, LapTime: Long): Track?

    @Modifying
    @Query("UPDATE Track t SET t.offset = t.offset + ?2 WHERE t.channel.id = ?1 AND t.offset > ?3 ORDER BY t.offset DESC")
    fun moveOffsetsByAmountAfterGiven(channelId: Long?, amount: Long, offset: Long)

    @Modifying
    @Query("UPDATE Track t SET t.offset = t.offset + ?2 WHERE t.channel.id = ?1 AND ?3 BETWEEN ?3 AND ?4 ORDER BY t.offset DESC")
    fun moveOffsetsByAmountBetweenGiven(channelId: Long?, amount: Long, oneBound: Long, secondBound: Long)
}
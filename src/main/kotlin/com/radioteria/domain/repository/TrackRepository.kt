package com.radioteria.domain.repository

import com.radioteria.domain.entity.Track
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface TrackRepository : Repository<Track, Long> {
    fun findOne(id: Long?): Track?
    fun findAllByChannelIdOrderByOffsetAsc(channelId: Long?): List<Track>

    fun save(track: Track)
    fun delete(track: Track)

    @Query("SELECT COALESCE(SUM(t.duration), 0) FROM Track t WHERE t.channel.id = ?1")
    fun getTracklistDurationByChannelId(id: Long?): Long

    @Query("FROM Track t WHERE t.offset <= ?2 AND t.ending > ?2 AND t.channel.id = ?1 ORDER BY t.offset ASC")
    fun findOneByChannelIdAndLapPosition(channelId: Long?, lapPosition: Long?): Track?

    fun findAllByChannelIdAndOffsetGreaterThan(channelId: Long?, amount: Long): List<Track>
    fun findAllByChannelIdAndOffsetGreaterThanEqual(channelId: Long?, amount: Long): List<Track>

    fun findAllByChannelIdAndOffsetBetween(channelId: Long?, from: Long, to: Long): List<Track>

    @Modifying
    @Query("UPDATE Track SET offset = offset + ?2 WHERE id IN ?1")
    fun increaseOffsetWhereIdIn(ids: Collection<Long>, amount: Long)
}
package com.radioteria.domain.repository

import com.radioteria.domain.entity.Channel
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface ChannelRepository : Repository<Channel, Long> {
    fun findOne(id: Long?): Channel?
    fun findAllByUserId(userId: Long): List<Channel>
    fun save(channel: Channel): Channel
    fun delete(channel: Channel)

    @Modifying
    @Query("UPDATE Channel c SET c.startedAt = c.startedAt + ?2 WHERE c.id = ?1 AND c.startedAt IS NOT NULL")
    fun increaseStartedAt(channelId: Long, amount: Long)
}
package com.radioteria.domain.repository

import com.radioteria.domain.entity.Channel
import org.springframework.data.repository.Repository

interface ChannelRepository : Repository<Channel, Long> {
    fun findOne(id: Long?): Channel?
    fun findAllByUserId(userId: Long): List<Channel>
    fun save(channel: Channel): Channel
    fun delete(channel: Channel)
}
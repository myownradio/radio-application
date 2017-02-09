package com.radioteria.domain.repository

import com.radioteria.domain.entity.Channel
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

interface ChannelRepository : Repository<Channel, Long> {
    fun findOne(id: Long?): Channel?
    fun findAllByUserId(@Param(value = "user_id") userId: Long): List<Channel>
    fun save(channel: Channel): Channel
}
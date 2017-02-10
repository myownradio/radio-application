package com.radioteria.web.request

import com.radioteria.domain.entity.Channel
import javax.validation.constraints.Size

data class ChannelRequest(
        @field:Size(min = 1, max = 64)
        val name: String = ""
) {
    fun fillChannel(channel: Channel) {
        channel.name = name
    }
}
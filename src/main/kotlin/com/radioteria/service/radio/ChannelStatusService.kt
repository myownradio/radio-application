package com.radioteria.service.radio

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track

interface ChannelStatusService {
    interface Status {
        data class TrackItem(val track: Track, val offset: Long): Status
        data class Nothing(val reason: String): Status
    }

    fun getStatus(channel: Channel): Status
}
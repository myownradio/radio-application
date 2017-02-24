package com.radioteria.service

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import org.springframework.web.multipart.MultipartFile

interface TrackService {
    fun upload(channel: Channel, file: MultipartFile): Track
}

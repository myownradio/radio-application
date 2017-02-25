package com.radioteria.service.core

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track

interface TrackService {
    fun upload(channel: Channel, uploadedFile: UploadedFile): Track
}

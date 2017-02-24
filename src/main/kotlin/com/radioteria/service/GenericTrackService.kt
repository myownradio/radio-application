package com.radioteria.service

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.service.audio.metadata.MetadataReader
import com.radioteria.service.fs.FileService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class GenericTrackService(
        val trackRepository: TrackRepository,
        val fileService: FileService,
        val metadataReader: MetadataReader
) : TrackService {

    override fun upload(channel: Channel, file: MultipartFile): Track {
        val metadata = metadataReader.read(file.name)
        val uploadedFile = fileService.put(file.originalFilename, { file.inputStream })
        val track = Track(
                title = metadata.title,
                artist = metadata.artist,
                duration = metadata.duration,
                channel = channel,
                audioFile = uploadedFile,
                position = 0
        )
        return track.apply { trackRepository.save(this) }
    }

}
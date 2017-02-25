package com.radioteria.service.core

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.service.audio.metadata.MetadataReader
import com.radioteria.service.fs.FileService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class GenericTrackService(
        val trackRepository: TrackRepository,
        val fileService: FileService,
        val metadataReader: MetadataReader
) : TrackService {

    @Transactional
    override fun upload(channel: Channel, uploadedFile: UploadedFile): Track {
        val metadata = metadataReader.read(uploadedFile.inputStream)
        val storedFile = fileService.put(uploadedFile.filename, { uploadedFile.inputStream })
        val track = Track(
                title = metadata.title,
                artist = metadata.artist,
                duration = metadata.duration,
                channel = channel,
                audioFile = storedFile,
                position = 0
        )
        return track.apply { trackRepository.save(this) }
    }

}
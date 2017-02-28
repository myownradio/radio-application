package com.radioteria.service.core

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import com.radioteria.domain.repository.TrackRepository
import com.radioteria.domain.service.ChannelTracklistService
import com.radioteria.service.audio.metadata.MetadataReader
import com.radioteria.service.fs.FileService
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class GenericTrackService(
        val trackRepository: TrackRepository,
        val channelTracklistService: ChannelTracklistService,
        val fileService: FileService,
        val metadataReader: MetadataReader
) : TrackService {

    @Transactional
    override fun upload(channel: Channel, uploadedFile: UploadedFile): Track {
        val metadata = metadataReader.read(uploadedFile.inputStream) ?:
                throw InvalidAudioFileException()

        val storedFile = fileService.put(uploadedFile.filename, { uploadedFile.inputStream })
        val track = Track(
                title = normalizeTrackTitle(metadata.title, uploadedFile),
                artist = metadata.artist,
                duration = metadata.duration,
                channel = channel,
                audioFile = storedFile
        )

        return track.apply { channelTracklistService.add(this) }
    }

    @Transactional
    override fun delete(track: Track) {
        fileService.delete(track.audioFile)
        trackRepository.delete(track)
    }

    private fun normalizeTrackTitle(title: String, uploadedFile: UploadedFile): String {
        return if (title.isEmpty()) mapFilenameToTitle(uploadedFile.filename) else title
    }

    private fun mapFilenameToTitle(filename: String): String {
        return FilenameUtils.getBaseName(filename)
    }

}
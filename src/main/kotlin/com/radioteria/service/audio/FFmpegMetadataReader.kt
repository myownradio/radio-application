package com.radioteria.service.audio

import org.springframework.stereotype.Service

// todo: may be it is possible to merge this service with ffmpegService
@Service
class FFmpegMetadataReader(val ffmpegService: FFmpegService): MetadataReader {

    override fun read(filename: String): Metadata {
        val result = ffmpegService.probe(filename)
        val duration = result.format.duration
        val tags = result.format.tags

        return Metadata(
                tags["title"] ?: "",
                tags["artist"] ?: "",

                convertDurationFromDouble(duration)
        )
    }

    private fun convertDurationFromDouble(duration: Double): Long {
        return (duration * 1000).toLong()
    }

}
package com.radioteria.service.audio

import org.springframework.stereotype.Service

@Service
class FFmpegMetadataReader(val ffmpegService: FFmpegService): MetadataReader {
    override fun read(filename: String): Metadata {
        val result = ffmpegService.probe(filename)
        val duration = result.format.duration
        val tags = result.format.tags

        return Metadata(tags["title"] ?: "", tags["artist"] ?: "", duration.toLong())
    }
}
package com.radioteria.service.audio

import com.radioteria.service.shell.BinaryLocator
import net.bramp.ffmpeg.FFprobe
import org.springframework.stereotype.Service

@Service
class FFmpegMetadataReader(binaryLocator: BinaryLocator): MetadataReader {

    private val ffprobeService: FFprobe = FFprobe(binaryLocator.locate("ffprobe"))

    override fun read(filename: String): Metadata {
        val result = ffprobeService.probe(filename)

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
package com.radioteria.service.audio

import com.radioteria.service.shell.BinaryLocator
import net.bramp.ffmpeg.FFprobe
import org.springframework.stereotype.Service
import java.io.File

@Service
class FFmpegMetadataReader(binaryLocator: BinaryLocator): MetadataReader {

    private val ffprobeService: FFprobe = FFprobe(binaryLocator.locate("ffprobe"))

    override fun read(file: String): Metadata {
        val result = ffprobeService.probe(file)

        val duration = result.format.duration
        val tags = result.format.tags

        return Metadata(
                tags["title"] ?: "",
                tags["artist"] ?: "",
                convertDurationFromDouble(duration)
        )
    }

    override fun read(file: File): Metadata {
        return read(file.absolutePath)
    }

    private fun convertDurationFromDouble(duration: Double): Long {
        return (duration * 1000).toLong()
    }

}
package com.radioteria.service.audio.metadata

import com.radioteria.config.spring.logging.Logging
import com.radioteria.service.shell.BinaryLocator
import com.peacefulbit.util.useAsFile
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.probe.FFmpegProbeResult
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import java.io.InputStream

@Logging
@Service
class FFprobeMetadataReader(binaryLocator: BinaryLocator): MetadataReader {

    private val ffprobeService: FFprobe = FFprobe(binaryLocator.locate("ffprobe"))

    override fun read(file: String): Metadata? {
        return probe(file)?.let {
            val duration = it.format.duration
            val tags = it.format.tags

            return Metadata(tags["title"].orEmpty(), tags["artist"].orEmpty(), duration.normalizeAsLong())
        }
    }

    private fun probe(file: String): FFmpegProbeResult? {
        return try { ffprobeService.probe(file) } catch (e: IOException) { null }
    }

    override fun read(file: File): Metadata? {
        return read(file.absolutePath)
    }

    override fun read(inputStream: InputStream): Metadata? {
        return inputStream.useAsFile { read(it) }
    }

    private fun Double.normalizeAsLong(): Long {
        return (this * 1000).toLong()
    }

    private fun String?.orEmpty(): String {
        return this ?: ""
    }

}
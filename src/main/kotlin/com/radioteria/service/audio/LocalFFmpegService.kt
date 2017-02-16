package com.radioteria.service.audio

import com.radioteria.service.shell.BinaryLocator
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.probe.FFmpegProbeResult
import org.springframework.stereotype.Service

@Service
class LocalFFmpegService(binaryLocator: BinaryLocator) : FFmpegService {
    private val ffprobeService: FFprobe = FFprobe(binaryLocator.locate("ffprobe"))

    override fun probe(filename: String): FFmpegProbeResult {
        return ffprobeService.probe(filename)
    }
}
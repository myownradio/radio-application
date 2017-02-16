package com.radioteria.service.audio

import net.bramp.ffmpeg.probe.FFmpegProbeResult

interface FFmpegService {
    fun probe(filename: String): FFmpegProbeResult
}
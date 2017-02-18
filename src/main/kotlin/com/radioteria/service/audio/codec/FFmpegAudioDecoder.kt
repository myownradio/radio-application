package com.radioteria.service.audio.codec

import com.radioteria.service.shell.BinaryLocator
import net.bramp.ffmpeg.FFmpeg
import org.springframework.stereotype.Service
import java.io.InputStream
import java.net.URL
import java.net.URLDecoder
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class FFmpegAudioDecoder(binaryLocator: BinaryLocator) : AudioDecoder {
    private val ffmpegService = FFmpeg(binaryLocator.locate("ffmpeg"))

    override fun decode(url: URL, skipMilliseconds: Long): InputStream {
        val job = ffmpegService.builder()
                .setInput(URLDecoder.decode(url.toString(), "utf-8"))
                .setStartOffset(skipMilliseconds, TimeUnit.MILLISECONDS)
                .addStdoutOutput()
                .setAudioChannels(2)
                .setAudioSampleRate(44100)
                .setAudioCodec("pcm_s16le")
                .setFormat("s16le")
                .done()
                .build()

        val command = ffmpegService.path(job).toTypedArray()

        return Runtime.getRuntime().exec(command).inputStream
    }
}
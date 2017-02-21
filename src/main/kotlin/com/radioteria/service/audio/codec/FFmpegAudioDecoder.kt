package com.radioteria.service.audio.codec

import com.radioteria.service.shell.BinaryLocator
import net.bramp.ffmpeg.FFmpeg
import org.springframework.stereotype.Service
import java.io.InputStream
import java.net.URL
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

@Service
class FFmpegAudioDecoder(binaryLocator: BinaryLocator) : AudioDecoder {

    companion object {
        const val AUDIO_FORMAT = "s16le"
        const val AUDIO_CODEC = "pcm_s16le"
    }

    private val ffmpegService = FFmpeg(binaryLocator.locate("ffmpeg"))

    override fun decode(url: URL, skipMilliseconds: Long): InputStream {
        val builder = ffmpegService.builder()
                .setInput(URLDecoder.decode(url.toString(), "utf-8"))
                .addStdoutOutput()
                    .setAudioChannels(2)
                    .setAudioSampleRate(44100)
                    .setAudioCodec(AUDIO_CODEC)
                    .setFormat(AUDIO_FORMAT)
                    .done()

        if (skipMilliseconds > 0) {
            builder.setStartOffset(skipMilliseconds, TimeUnit.MILLISECONDS)
        }

        val job = builder.build()

        val command = ffmpegService.path(job).toTypedArray()

        return Runtime.getRuntime().exec(command).inputStream
    }

}
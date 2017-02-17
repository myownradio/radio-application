package com.radioteria.service.audio.play

import com.radioteria.service.shell.BinaryLocator
import com.radioteria.service.shell.TransientProcess
import net.bramp.ffmpeg.FFmpeg
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.OutputStream

@Service
class FFmpegAudioCodec(binaryLocator: BinaryLocator) : AudioCodec {

    private val ffmpegService: FFmpeg = FFmpeg(binaryLocator.locate("ffmpeg"))

    override fun decode(from: InputStream, to: OutputStream) {
        val job = ffmpegService.builder()
                .setInput("-")
                .addStdoutOutput()
                    .setAudioChannels(2)
                    .setAudioSampleRate(44100)
                    .setAudioCodec("pcm_s16le")
                    .setFormat("s16le")
                    .done()
                .build()

        TransientProcess.run(ffmpegService.path(job), from, to)
    }

    override fun encode(from: InputStream, to: OutputStream) {
        throw UnsupportedOperationException("not implemented")
    }

}
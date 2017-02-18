package com.radioteria.service

import com.radioteria.service.audio.codec.FFmpegAudioDecoder
import com.radioteria.service.shell.ShellBinaryLocator
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Test
import org.springframework.util.ResourceUtils
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

class FFmpegAudioCodecTest {

    val binaryLocator = ShellBinaryLocator()
    val audioDecoder = FFmpegAudioDecoder(binaryLocator)

    @Test
    fun decode() {
        val audioFile = ResourceUtils.getURL("classpath:fixtures/ffprobe-test.mp3")

        val outputStream = ByteArrayOutputStream()

        val rawStream = audioDecoder.decode(audioFile)

        copyFragment(rawStream, outputStream, 1024768)

        rawStream.close()

        assertThat(outputStream.size(), greaterThan(0))
    }

    fun copyFragment(inputStream: InputStream, outputStream: OutputStream, limit: Long) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var counter = 0
        var bytes = inputStream.read(buffer)

        while (bytes > 0 && counter < limit) {
            counter += bytes
            outputStream.write(buffer, 0, bytes)
            bytes = inputStream.read(buffer)
        }
    }

}
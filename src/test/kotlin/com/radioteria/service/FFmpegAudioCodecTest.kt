package com.radioteria.service

import com.radioteria.service.audio.play.AudioCodec
import com.radioteria.service.audio.play.FFmpegAudioCodec
import com.radioteria.service.shell.BinaryLocator
import com.radioteria.service.shell.ShellBinaryLocator
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Ignore
import org.junit.Test
import org.springframework.util.ResourceUtils
import java.io.ByteArrayOutputStream

@Ignore
class FFmpegAudioCodecTest {

    val binaryLocator: BinaryLocator = ShellBinaryLocator()
    val audioCodec: AudioCodec = FFmpegAudioCodec(binaryLocator)

    @Test
    fun decode() {
        val audioFile = ResourceUtils.getFile("classpath:fixtures/ffprobe-test.mp3")

        val outputStream = ByteArrayOutputStream()
        val inputStream = audioFile.inputStream()

        audioCodec.decode(inputStream, outputStream)
//
//        inputStream.close()
//        outputStream.close()

        assertThat(outputStream.size(), greaterThan(0))
    }

}
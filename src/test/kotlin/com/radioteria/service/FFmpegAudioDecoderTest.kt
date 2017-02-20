package com.radioteria.service

import com.radioteria.service.audio.codec.FFmpegAudioDecoder
import com.radioteria.service.shell.ShellBinaryLocator
import com.radioteria.util.io.CountableOutputStream
import com.radioteria.util.io.NullOutputStream
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Test
import org.springframework.util.ResourceUtils

class FFmpegAudioDecoderTest {

    val binaryLocator = ShellBinaryLocator()
    val audioDecoder = FFmpegAudioDecoder(binaryLocator)

    @Test
    fun decode() {

        val sourceCompressedAudioFile = ResourceUtils.getURL("classpath:fixtures/ffprobe-test.mp3")

        val countableOutputStream = CountableOutputStream(NullOutputStream())

        val decoderInputStream = audioDecoder.decode(sourceCompressedAudioFile)

        decoderInputStream.copyTo(countableOutputStream)
        decoderInputStream.close()

        assertThat(countableOutputStream.bytes, greaterThan(0L))

        System.err.println("Bytes decoded: ${countableOutputStream.bytes}")
    }

}
package com.radioteria.service

import com.radioteria.service.audio.codec.FFmpegAudioDecoder
import com.radioteria.service.shell.ShellBinaryLocator
import com.radioteria.util.io.CountableOutputStream
import com.radioteria.util.io.NullOutputStream
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Test
import org.springframework.util.ResourceUtils
import java.net.URL

class AudioDecoderTest {

    companion object {
        val REMOTE_TEST_FILE = "http://myownradio.biz/test/jingle.wav"
    }

    val binaryLocator = ShellBinaryLocator()
    val audioDecoder = FFmpegAudioDecoder(binaryLocator)

    @Test
    fun fileProtocol() {
        val sourceCompressedAudioFile = ResourceUtils.getURL("classpath:fixtures/ffprobe-test.mp3")

        decodeAndVerify(sourceCompressedAudioFile)
    }

    @Test
    fun httpProtocol() {
        val sourceHttpAudioFile = URL(REMOTE_TEST_FILE)

        decodeAndVerify(sourceHttpAudioFile)
    }

    private fun decodeAndVerify(audioFileUrl: URL) {
        val countableOutputStream = CountableOutputStream(NullOutputStream())

        val decoderInputStream = audioDecoder.decode(audioFileUrl)

        decoderInputStream.copyTo(countableOutputStream)
        decoderInputStream.close()

        assertThat(countableOutputStream.bytes, greaterThan(0L))

        System.err.println("Bytes decoded: ${countableOutputStream.bytes}")
    }

}
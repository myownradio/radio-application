package com.radioteria.service

import com.radioteria.service.audio.*
import com.radioteria.service.shell.BinaryLocator
import com.radioteria.service.shell.ShellBinaryLocator
import org.junit.Test

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.springframework.util.ResourceUtils

// todo: replace this stub by real ffprobe test
class AudioMetadataReaderTest {

    companion object {
        val SAMPLE_TITLE = "sample title"
        val SAMPLE_ARTIST = "sample artist"
        val SAMPLE_DURATION = 27752L
    }

    val binaryLocator: BinaryLocator = ShellBinaryLocator()
    val ffmpegService: FFmpegService = LocalFFmpegService(binaryLocator)
    val metadataReader: MetadataReader = FFmpegMetadataReader(ffmpegService)

    @Test
    fun readSampleAudioMetadata() {
        val audioFile = ResourceUtils.getFile("classpath:fixtures/ffprobe-test.mp3").absolutePath
        val metadata = metadataReader.read(audioFile)

        assertThat(metadata.title, equalTo(SAMPLE_TITLE))
        assertThat(metadata.artist, equalTo(SAMPLE_ARTIST))
        assertThat(metadata.duration, equalTo(SAMPLE_DURATION))
    }

}
package com.radioteria.service

import com.radioteria.service.audio.*
import com.radioteria.service.shell.BinaryLocator
import com.radioteria.service.shell.ShellBinaryLocator
import org.junit.Test

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.springframework.util.ResourceUtils

class FFmpegMetadataReaderTest {

    companion object {
        val EXPECTED_TITLE = "sample title"
        val EXPECTED_ARTIST = "sample artist"
        val EXPECTED_DURATION = 27752L
    }

    val binaryLocator: BinaryLocator = ShellBinaryLocator()
    val metadataReader: MetadataReader = FFmpegMetadataReader(binaryLocator)

    @Test
    fun readSampleAudioMetadata() {
        val audioFile = ResourceUtils.getFile("classpath:fixtures/ffprobe-test.mp3")
        val metadata = metadataReader.read(audioFile)

        assertThat(metadata.title, equalTo(EXPECTED_TITLE))
        assertThat(metadata.artist, equalTo(EXPECTED_ARTIST))
        assertThat(metadata.duration, equalTo(EXPECTED_DURATION))
    }

}
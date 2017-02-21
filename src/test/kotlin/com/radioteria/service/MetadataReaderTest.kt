package com.radioteria.service

import com.radioteria.service.audio.metadata.MetadataReader
import org.junit.Test

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.ResourceUtils

@SpringBootTest
@RunWith(SpringRunner::class)
class MetadataReaderTest {

    companion object {
        val EXPECTED_TITLE = "sample title"
        val EXPECTED_ARTIST = "sample artist"
        val EXPECTED_DURATION = 27752L
    }

    @Autowired lateinit var metadataReader: MetadataReader

    @Test
    fun readSampleAudioMetadata() {
        val audioFile = ResourceUtils.getFile("classpath:fixtures/ffprobe-test.mp3")
        val metadata = metadataReader.read(audioFile)

        assertThat(metadata.title, equalTo(EXPECTED_TITLE))
        assertThat(metadata.artist, equalTo(EXPECTED_ARTIST))
        assertThat(metadata.duration, equalTo(EXPECTED_DURATION))
    }

}
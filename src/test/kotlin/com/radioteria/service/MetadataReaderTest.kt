package com.radioteria.service

import com.radioteria.service.audio.metadata.Metadata
import com.radioteria.service.audio.metadata.MetadataReader
import org.junit.Test

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.greaterThan
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.ResourceUtils
import java.io.FileInputStream

@SpringBootTest
@RunWith(SpringRunner::class)
class MetadataReaderTest {

    companion object {
        val EXPECTED_TITLE = "sample title"
        val EXPECTED_ARTIST = "sample artist"
        val EXPECTED_DURATION = 27000L
    }

    @Autowired lateinit var metadataReader: MetadataReader

    @Test
    fun readSampleAudioMetadataUsingFile() {
        val audioFile = ResourceUtils.getFile("classpath:fixtures/with_metadata.mp3")
        val metadata = metadataReader.read(audioFile)!!

        verifyMetadata(metadata)
    }

    @Test
    fun readSampleAudioMetadataUsingInputStream() {
        val audioFile = ResourceUtils.getFile("classpath:fixtures/with_metadata.mp3")
        val inputStream = FileInputStream(audioFile)

        val metadata = metadataReader.read(inputStream)!!

        verifyMetadata(metadata)
    }

    @Test
    fun readSampleAudioWithoutMetadataUsingFile() {
        val audioFile = ResourceUtils.getFile("classpath:fixtures/without_metadata.mp3")
        val metadata = metadataReader.read(audioFile)!!

        verifyNoMetadata(metadata)
    }


    private fun verifyMetadata(metadata: Metadata) {
        assertThat(metadata.title, equalTo(EXPECTED_TITLE))
        assertThat(metadata.artist, equalTo(EXPECTED_ARTIST))
        assertThat(metadata.duration, greaterThan(EXPECTED_DURATION))
    }

    private fun verifyNoMetadata(metadata: Metadata) {
        assertThat(metadata.title, equalTo(""))
        assertThat(metadata.artist, equalTo(""))
        assertThat(metadata.duration, greaterThan(EXPECTED_DURATION))
    }

}
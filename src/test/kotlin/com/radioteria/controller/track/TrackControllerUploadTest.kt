package com.radioteria.controller.track

import com.radioteria.controller.AbstractControllerTest
import org.junit.Test
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.ResourceUtils
import java.io.FileInputStream

import com.radioteria.controller.ControllerTestConstants.THAT_USER
import com.radioteria.controller.ControllerTestConstants.THIS_USER
import com.sun.javafx.scene.shape.PathUtils
import org.apache.commons.io.FilenameUtils

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class TrackControllerUploadTest : AbstractControllerTest() {

    @Test
    fun uploadTrackWhenAuthorized() {
        val fileMock = mockAudioFileWithMetadata()

        mvc.perform(
                fileUpload("/api/channel/6/track")
                        .file(fileMock)
                        .with(user(getUserDetails(THIS_USER))))

                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.title").value("sample title"))
                .andExpect(jsonPath("$.artist").value("sample artist"))

    }

    @Test
    fun uploadTrackWithoutMetadata() {
        val fileMock = mockAudioFileWithoutMetadata()

        mvc.perform(
                fileUpload("/api/channel/6/track")
                        .file(fileMock)
                        .with(user(getUserDetails(THIS_USER))))

                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.title").value("without_metadata"))
                .andExpect(jsonPath("$.artist").value(""))
    }

    @Test
    fun uploadForbiddenWhenUnauthorized() {
        val fileMock = mockAudioFileWithMetadata()

        mvc.perform(
                fileUpload("/api/channel/6/track")
                        .file(fileMock)
                        .with(user(getUserDetails(THAT_USER))))

                .andExpect(status().isForbidden)
    }

    @Test
    fun uploadDeniedWhenUnauthenticated() {
        val fileMock = mockAudioFileWithMetadata()

        mvc.perform(fileUpload("/api/channel/6/track").file(fileMock))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun uploadErrorWhenInvalidChannel() {
        val fileMock = mockAudioFileWithMetadata()

        mvc.perform(
                fileUpload("/api/channel/10/track")
                        .file(fileMock)
                        .with(user(getUserDetails(THIS_USER))))

                .andExpect(status().isNotFound)
    }

    @Test
    fun uploadErrorWhenInvalidAudioFile() {
        val fileMock = mockInvalidAudioFile()

        mvc.perform(fileUpload("/api/channel/6/track")
                .file(fileMock)
                .with(user(getUserDetails(THIS_USER))))

                .andExpect(status().isBadRequest)
    }

    private fun mockAudioFileWithMetadata(): MockMultipartFile {
        return mockResourceFile("classpath:fixtures/with_metadata.mp3")
    }

    private fun mockAudioFileWithoutMetadata(): MockMultipartFile {
        return mockResourceFile("classpath:fixtures/without_metadata.mp3")
    }

    private fun mockInvalidAudioFile(): MockMultipartFile {
        return mockResourceFile("classpath:fixtures/invalid.mp3")
    }

    private fun mockResourceFile(path: String): MockMultipartFile {
        val fileToUpload = ResourceUtils.getFile(path)
        val fileInputStream = FileInputStream(fileToUpload)
        val originalFilename = FilenameUtils.getName(path)
        val fileMock = MockMultipartFile("file", originalFilename, "audio/mpeg", fileInputStream)
        return fileMock
    }

}
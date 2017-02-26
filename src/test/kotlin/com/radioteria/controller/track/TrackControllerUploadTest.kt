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
    fun uploadErrorInvalidAudioFile() {
        val fileMock = mockInvalidAudioFile()

        mvc.perform(fileUpload("/api/channel/6/track")
                .file(fileMock)
                .with(user(getUserDetails(THIS_USER))))

                .andExpect(status().isBadRequest)
    }

    private fun mockAudioFileWithMetadata(): MockMultipartFile {
        return mockResourceFile("classpath:fixtures/ffprobe-test.mp3")
    }

    private fun mockInvalidAudioFile(): MockMultipartFile {
        return mockResourceFile("classpath:fixtures/invalid.mp3")
    }

    private fun mockResourceFile(path: String): MockMultipartFile {
        val fileToUpload = ResourceUtils.getFile(path)
        val fileInputStream = FileInputStream(fileToUpload)
        val fileMock = MockMultipartFile("file", fileInputStream)
        return fileMock
    }

}
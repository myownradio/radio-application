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
        val fileToUpload = ResourceUtils.getFile("classpath:fixtures/ffprobe-test.mp3")
        val fileInputStream = FileInputStream(fileToUpload)
        val fileMock = MockMultipartFile("file", fileInputStream)

        mvc.perform(
                fileUpload("/api/channel/6/track")
                        .file(fileMock)
                        .with(user(getUserDetails(THIS_USER))))

                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("sample title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist").value("sample artist"))

    }

    @Test
    fun uploadForbiddenWhenUnauthorized() {
        val fileToUpload = ResourceUtils.getFile("classpath:fixtures/ffprobe-test.mp3")
        val fileInputStream = FileInputStream(fileToUpload)
        val fileMock = MockMultipartFile("file", fileInputStream)

        mvc.perform(
                fileUpload("/api/channel/6/track")
                        .file(fileMock)
                        .with(user(getUserDetails(THAT_USER))))

                .andExpect(status().isForbidden)
    }

}
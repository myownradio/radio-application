package com.radioteria.controller

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.junit.Test
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.ResourceUtils
import java.io.FileInputStream

class TrackControllerTest : AbstractControllerTest() {

    companion object {
        const val RIGHT_USER = "user@mail.com"
        const val OTHER_USER = "user2@mail.com"
    }

    @Test
    fun getWhenAuthenticated() {
        mvc.perform(get("/api/channel/6/track/1").with(user(getUserDetails(RIGHT_USER))))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.title").value("Children"))
    }

    @Test
    fun getWhenAuthenticatedAsOtherUser() {
        mvc.perform(get("/api/channel/6/track/1").with(user(getUserDetails(OTHER_USER))))
                .andExpect(status().isForbidden)
    }

    @Test
    fun getWhenNotAuthenticated() {
        mvc.perform(get("/api/channel/6/track/1"))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun uploadTrackWhenAuthenticated() {
        val fileToUpload = ResourceUtils.getFile("classpath:fixtures/ffprobe-test.mp3")
        val fileInputStream = FileInputStream(fileToUpload)
        val fileMock = MockMultipartFile("file", fileInputStream)

        mvc.perform(
                fileUpload("/api/channel/6/track")
                        .file(fileMock)
                        .with(user(getUserDetails(RIGHT_USER))))

                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.title").value("sample title"))
                .andExpect(jsonPath("$.artist").value("sample artist"))

    }

}
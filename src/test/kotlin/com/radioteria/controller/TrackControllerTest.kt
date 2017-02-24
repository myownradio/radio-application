package com.radioteria.controller

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.junit.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class TrackControllerTest : AbstractControllerTest() {

    companion object {
        const val ONE_USER = "user@mail.com"
        const val OTHER_USER = "user2@mail.com"
    }

    @Test
    fun getWhenAuthenticated() {
        mvc.perform(get("/api/channel/6/track/1").with(user(getUserDetails(ONE_USER))))
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

}
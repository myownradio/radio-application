package com.radioteria.controller

import com.radioteria.controller.ControllerTestConstants.OTHER_USER
import com.radioteria.controller.ControllerTestConstants.THIS_USER
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.junit.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class TrackControllerTest : AbstractControllerTest() {

    @Test
    fun getWhenAuthenticated() {
        mvc.perform(get("/api/channel/6/track/1").with(user(getUserDetails(THIS_USER))))
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
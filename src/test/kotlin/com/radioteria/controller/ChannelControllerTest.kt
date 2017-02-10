package com.radioteria.controller

import com.radioteria.util.withBody
import com.radioteria.web.request.NewChannelRequest
import org.junit.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ChannelControllerTest : AbstractControllerTest() {
    companion object {
        const val API_CHANNEL_ENDPOINT = "/api/channel"
        const val CHANNEL_GOOD_NAME = "Beautiful Channel"
        const val CHANNEL_SHORT_NAME = ""

        const val ACTIVE_USER_EMAIL = "user@mail.com"
    }

    @Test
    fun testCreateWhenAuthorizedAsActiveUser() {
        val request = post(API_CHANNEL_ENDPOINT)
                .with(user(getUserDetails(ACTIVE_USER_EMAIL)))
                .withBody(NewChannelRequest(name = CHANNEL_GOOD_NAME))

        mvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value(CHANNEL_GOOD_NAME))
    }

    @Test
    fun testCreateDenyWithShortName() {
        val request = post(API_CHANNEL_ENDPOINT)
                .with(user(getUserDetails(ACTIVE_USER_EMAIL)))
                .withBody(NewChannelRequest(name = CHANNEL_SHORT_NAME))

        mvc.perform(request)
                .andExpect(status().isBadRequest)
    }

    @Test
    fun testCreateDenyWhenUnauthorized() {
        val request = post(API_CHANNEL_ENDPOINT)
                .with(csrf())
                .withBody(NewChannelRequest(name = CHANNEL_GOOD_NAME))

        mvc.perform(request)
                .andExpect(status().isUnauthorized)
    }
}
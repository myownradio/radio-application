package com.radioteria.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.radioteria.util.withBody
import com.radioteria.web.form.NewChannelForm
import org.junit.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ChannelControllerTest : AbstractControllerTest() {

    companion object {
        const val API_CHANNEL_ENDPOINT = "/api/channel"
        const val CHANNEL_NAME = "Beautiful Channel"
    }

    @Test
    fun testCreateDenyWhenUnauthorized() {
        val request = post(API_CHANNEL_ENDPOINT)
                .with(csrf())
                .param("name", CHANNEL_NAME)

        mvc.perform(request)
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun testCreateWhenAuthorizedAsActiveUser() {
        val request = post(API_CHANNEL_ENDPOINT)
                .with(user(userDetails))
                .contentType("application/json")
                .withBody(NewChannelForm(name = CHANNEL_NAME))

        mvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value(CHANNEL_NAME))
    }
}
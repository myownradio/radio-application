package com.radioteria.controller

import com.radioteria.util.withBody
import com.radioteria.web.request.ChannelRequest
import org.junit.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ChannelControllerTest : AbstractControllerTest() {
    companion object {
        const val API_CHANNEL_ENDPOINT = "/api/channel"
        const val CHANNEL_GOOD_NAME = "Beautiful Channel"
        const val CHANNEL_SHORT_NAME = ""

        const val ACTIVE_USER_EMAIL = "user@mail.com"
        const val OWN_CHANNEL_ID = 1
        const val OWN_CHANNEL_NAME = "Radio #1"

        const val FOREIGN_CHANNEL_ID = 3

        const val UPDATED_CHANNEL_NAME = "New Radio #1"
    }

    @Test
    fun createChannelWhenAuthorizedAsActiveUser() {
        mvc.perform(
                post(API_CHANNEL_ENDPOINT)
                        .with(user(getUserDetails(ACTIVE_USER_EMAIL)))
                        .withBody(ChannelRequest(name = CHANNEL_GOOD_NAME))
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value(CHANNEL_GOOD_NAME))
    }

    @Test
    fun createDenyIfNameIsShort() {
        mvc.perform(
                post(API_CHANNEL_ENDPOINT)
                        .with(user(getUserDetails(ACTIVE_USER_EMAIL)))
                        .withBody(ChannelRequest(name = CHANNEL_SHORT_NAME))
        )
                .andExpect(status().isBadRequest)
    }

    @Test
    fun createDenyWhenUnauthorized() {
        mvc.perform(
                post(API_CHANNEL_ENDPOINT)
                        .with(csrf())
                        .withBody(ChannelRequest(name = CHANNEL_GOOD_NAME))
        )
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun getOwnChannelWhenAuthorized() {
        mvc.perform(
                get("$API_CHANNEL_ENDPOINT/$OWN_CHANNEL_ID")
                        .with(user(getUserDetails(ACTIVE_USER_EMAIL)))
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value(OWN_CHANNEL_NAME))
    }

    @Test
    fun getForeignChannelWhenAuthorized() {
        mvc.perform(
                get("$API_CHANNEL_ENDPOINT/$FOREIGN_CHANNEL_ID")
                        .with(user(getUserDetails(ACTIVE_USER_EMAIL)))
        )
                .andExpect(status().isForbidden)
    }

    @Test
    fun getChannelWhenUnauthorized() {
        mvc.perform(get("$API_CHANNEL_ENDPOINT/$OWN_CHANNEL_ID"))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun getOwnChannelsList() {
        mvc.perform(
                get(API_CHANNEL_ENDPOINT)
                        .with(user(getUserDetails(ACTIVE_USER_EMAIL)))
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(3))
    }

    @Test
    fun getChannelsWhenUnauthorized() {
        mvc.perform(
                get(API_CHANNEL_ENDPOINT)
        )
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun updateOwnChannel() {
        val user = user(getUserDetails(ACTIVE_USER_EMAIL))

        mvc.perform(
                put("$API_CHANNEL_ENDPOINT/$OWN_CHANNEL_ID")
                        .with(user)
                        .withBody(ChannelRequest(name = UPDATED_CHANNEL_NAME))
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value(UPDATED_CHANNEL_NAME))

        mvc.perform(
                get("$API_CHANNEL_ENDPOINT/$OWN_CHANNEL_ID")
                        .with(user)
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value(UPDATED_CHANNEL_NAME))
    }

    @Test
    fun updateForeignChannel() {
        mvc.perform(
                put("$API_CHANNEL_ENDPOINT/$FOREIGN_CHANNEL_ID")
                        .with(user(getUserDetails(ACTIVE_USER_EMAIL)))
                        .withBody(ChannelRequest(name = UPDATED_CHANNEL_NAME))
        )
                .andExpect(status().isForbidden)
    }

    @Test
    fun deleteOwnChannel() {
        val user = user(getUserDetails(ACTIVE_USER_EMAIL))

        mvc.perform(delete("$API_CHANNEL_ENDPOINT/$OWN_CHANNEL_ID").with(user))
                .andExpect(status().isOk)

        mvc.perform(get(API_CHANNEL_ENDPOINT).with(user))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(2))
    }

    @Test
    fun deleteForeignChannel() {
        val user = user(getUserDetails(ACTIVE_USER_EMAIL))

        mvc.perform(delete("$API_CHANNEL_ENDPOINT/$FOREIGN_CHANNEL_ID").with(user))
                .andExpect(status().isForbidden)
    }
}
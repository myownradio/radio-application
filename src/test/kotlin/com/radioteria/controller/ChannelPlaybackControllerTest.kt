package com.radioteria.controller

import com.radioteria.controller.ControllerTestConstants.OTHER_USER
import com.radioteria.controller.ControllerTestConstants.PLAYING_CHANNEL_ID
import com.radioteria.controller.ControllerTestConstants.STOPPED_CHANNEL_ID
import com.radioteria.controller.ControllerTestConstants.THIS_USER
import com.radioteria.controller.ControllerTestConstants.TRACKLESS_CHANNEL_ID
import org.hamcrest.CoreMatchers.isA
import org.junit.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ChannelPlaybackControllerTest : AbstractControllerTest() {

    fun nowPlayingUrl(channelId: Int): String ="/api/public/channel/$channelId/now"

    @Test
    fun startChannel() {
        mvc.perform(get(nowPlayingUrl(STOPPED_CHANNEL_ID)))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").exists())

        mvc.perform(post("/api/channel/$STOPPED_CHANNEL_ID/control/start")
                .with(user(getUserDetails(THIS_USER))))
                .andExpect(status().isOk)

        mvc.perform(get(nowPlayingUrl(STOPPED_CHANNEL_ID)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.timePosition").value(0))
                .andExpect(jsonPath("$.track.id").value(3))
    }

    @Test
    fun stopChannel() {
        mvc.perform(get(nowPlayingUrl(PLAYING_CHANNEL_ID)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.track").exists())
                .andExpect(jsonPath("$.timePosition").exists())

        mvc.perform(post("/api/channel/$PLAYING_CHANNEL_ID/control/stop")
                .with(user(getUserDetails(THIS_USER))))
                .andExpect(status().isOk)

        mvc.perform(get(nowPlayingUrl(PLAYING_CHANNEL_ID)))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun startEmptyChannel() {
        mvc.perform(post("/api/channel/$TRACKLESS_CHANNEL_ID/control/start")
                .with(user(getUserDetails(THIS_USER))))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun startChannelUnauthorized() {
        mvc.perform(post("/api/channel/$TRACKLESS_CHANNEL_ID/control/start"))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun startChannelDifferentUser() {
        mvc.perform(post("/api/channel/$TRACKLESS_CHANNEL_ID/control/start")
                .with(user(getUserDetails(OTHER_USER))))
                .andExpect(status().isForbidden)
    }

}
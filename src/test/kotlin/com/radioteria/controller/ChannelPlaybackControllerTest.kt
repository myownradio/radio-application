package com.radioteria.controller

import com.radioteria.controller.ControllerTestConstants.PLAYING_CHANNEL_ID
import com.radioteria.controller.ControllerTestConstants.STOPPED_CHANNEL_ID
import com.radioteria.controller.ControllerTestConstants.THIS_USER
import com.radioteria.controller.ControllerTestConstants.TRACKLESS_CHANNEL_ID
import org.junit.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ChannelPlaybackControllerTest : AbstractControllerTest() {

    @Test
    fun startChannel() {
        mvc.perform(get("/api/public/channel/$STOPPED_CHANNEL_ID/now"))
                .andExpect(status().isBadRequest)

        mvc.perform(post("/api/channel/$STOPPED_CHANNEL_ID/control/start")
                .with(user(getUserDetails(THIS_USER))))
                .andExpect(status().isOk)

        mvc.perform(get("/api/public/channel/$STOPPED_CHANNEL_ID/now"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.timePosition").value(0))
                .andExpect(jsonPath("$.track.id").value(3))
    }

    @Test
    fun stopChannel() {
        mvc.perform(get("/api/public/channel/$PLAYING_CHANNEL_ID/now"))
                .andExpect(status().isOk)

        mvc.perform(post("/api/channel/$PLAYING_CHANNEL_ID/control/stop")
                .with(user(getUserDetails(THIS_USER))))
                .andExpect(status().isOk)

        mvc.perform(get("/api/public/channel/$STOPPED_CHANNEL_ID/now"))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun startEmptyChannel() {
        mvc.perform(post("/api/channel/$TRACKLESS_CHANNEL_ID/control/start")
                .with(user(getUserDetails(THIS_USER))))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun startChannelUnauthorized() {
        mvc.perform(post("/api/channel/$TRACKLESS_CHANNEL_ID/control/start"))
                .andExpect(status().isUnauthorized)
    }

}
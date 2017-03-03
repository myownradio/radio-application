package com.radioteria.controller.public

import org.junit.Test

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import com.radioteria.controller.AbstractControllerTest
import com.radioteria.controller.ControllerTestConstants.PLAYING_CHANNEL_ID
import com.radioteria.controller.ControllerTestConstants.STOPPED_CHANNEL_ID
import com.radioteria.controller.ControllerTestConstants.TRACKLESS_CHANNEL_ID
import org.hamcrest.Matchers.*

class NowPlayingControllerTest : AbstractControllerTest() {
    @Test
    fun nowPlayingOnChannel() {
        mvc.perform(get("/api/public/channel/$PLAYING_CHANNEL_ID/now"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.track.channel.id").value(6))
    }

    @Test
    fun nowPlayingOnStoppedChannel() {
        mvc.perform(get("/api/public/channel/$STOPPED_CHANNEL_ID/now"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value(containsString("not started")))
    }

    @Test
    fun nowPlayingOnHasNoTracksChannel() {
        mvc.perform(get("/api/public/channel/$TRACKLESS_CHANNEL_ID/now"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value(containsString("has no tracks")))
    }
}
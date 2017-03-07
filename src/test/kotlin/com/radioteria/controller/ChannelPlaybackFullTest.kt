package com.radioteria.controller

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.radioteria.domain.service.NowPlayingService
import junit.framework.Assert.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

import org.hamcrest.CoreMatchers.*
import org.junit.Ignore
import org.mockito.ArgumentCaptor
import org.mockito.internal.matchers.CapturingMatcher
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultActions.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@Ignore
class ChannelPlaybackFullTest : AbstractControllerTest() {

    val channelId = 7
    val channelOwner = "user@mail.com"

    fun getNowPlayingUrl(): String = "/api/public/channel/$channelId/now"
    fun getChannelActionUrl(action: String) = "/api/channel/$channelId/control/$action"

    @Test
    fun testEverything() {
        assertThatChannelIsStopped()
        startChannelFromBegin()
        assertThatPlayingFirstTrack()
        skipAHalfOfFirstTrack()
        // scroll through a half of first track
        // assert that scrolled through a half of first track
        // scroll to next track (skip)
        // assert that at the beginning of second track
        // scroll through a half of second track
        // assert that we are at the half...
        // rewind current track
        // assert that we are at the begin of second track again
        // skip track
        // assert that we are on third track
        // skip track
        // assert that we are at the beginning of playlist
    }

    private fun skipAHalfOfFirstTrack() {
        val matcher = CapturingMatcher<Long>()

        val timeToSkip = matcher.lastValue / 2

        mvc.perform(
                post(getChannelActionUrl("seek"))
                        .param("amount", timeToSkip.toString())
                        .with(user(getUserDetails(channelOwner)))
        )
    }

    private fun assertThatPlayingFirstTrack() {
        val nowPlaying = getNowPlaying()
//                .andExpect(jsonPath("$.timePosition").value(0))
//                .andExpect(jsonPath("$.track.offset").value(0))
    }

    private fun assertThatChannelIsStopped() {
        mvc.perform(get(getNowPlayingUrl()))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.message").value(containsString("is not started")))
    }

    private fun startChannelFromBegin() {
        mvc.perform(post(getChannelActionUrl("start"))
                .with(user(getUserDetails(channelOwner))))
                .andExpect(status().isOk)
    }

    private fun getNowPlaying(): NowPlayingService.NowPlaying {
        val response = mvc.perform(get(getNowPlayingUrl()))
                .andExpect(status().isOk)
                .andReturn()

        val content = response.response.contentAsByteArray




        val mapper = ObjectMapper()

        return mapper.readValue(content, NowPlayingService.NowPlaying::class.java)
    }

}
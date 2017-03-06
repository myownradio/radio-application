package com.radioteria.web.controller

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.service.ChannelPlaybackService
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/channel/{channelId}/control")
@Secured("ROLE_USER")
@RestController
class ChannelPlaybackController(
        val channelPlaybackService: ChannelPlaybackService
) {

    @PreAuthorize("#channel.belongsTo(principal.user)")
    @PostMapping("start")
    fun start(@PathVariable("channelId") channel: Channel) {
        channelPlaybackService.startChannel(channel)
    }

    @PreAuthorize("#channel.belongsTo(principal.user)")
    @PostMapping("stop")
    fun stop(@PathVariable("channelId") channel: Channel) {
        channelPlaybackService.stopChannel(channel)
    }

}
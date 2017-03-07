package com.radioteria.web.controller

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import com.radioteria.domain.service.ChannelPlaybackService
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

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

    @PreAuthorize("#channel.belongsTo(principal.user) and #track.belongsTo(#channel)")
    @PostMapping("start/{trackId}")
    fun startFromTrack(
            @PathVariable("channelId") channel: Channel,
            @PathVariable("trackId") track: Track
    ) {
        channelPlaybackService.startChannelFromTimePosition(channel, track.offset)
    }

    @PreAuthorize("#channel.belongsTo(principal.user)")
    @PostMapping("stop")
    fun stop(@PathVariable("channelId") channel: Channel) {
        channelPlaybackService.stopChannel(channel)
    }

    @PreAuthorize("#channel.belongsTo(principal.user)")
    @PostMapping("seek")
    fun seek(@PathVariable("channelId") channel: Channel, @RequestParam("amount") amount: Long) {
        channelPlaybackService.seekChannel(channel, amount)
    }


    @PreAuthorize("#channel.belongsTo(principal.user)")
    @PostMapping("skip")
    fun skip(@PathVariable("channelId") channel: Channel) {
        channelPlaybackService.skipTrackOnChannel(channel)
    }


    @PreAuthorize("#channel.belongsTo(principal.user)")
    @PostMapping("rewind")
    fun rewind(@PathVariable("channelId") channel: Channel) {
        channelPlaybackService.rewindTrackOnChannel(channel)
    }

}
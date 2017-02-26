package com.radioteria.web.controller

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import com.radioteria.service.core.TrackService
import com.radioteria.service.core.UploadedFile
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Secured("ROLE_USER")
@ExposesResourceFor(Track::class)
@RequestMapping("/api/channel/{channelId}/track")
@RestController
class TrackController(val trackService: TrackService) {

    companion object {
        const val CHANNEL_PRE_AUTH = "#channel.belongsTo(principal.user)"
        const val TRACK_AND_CHANNEL_PRE_AUTH = "#channel.belongsTo(principal.user) and #track.belongsTo(#channel)"
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(CHANNEL_PRE_AUTH)
    @PostMapping
    fun upload(@PathVariable("channelId") channel: Channel, @RequestParam("file") file: MultipartFile): Track {
        val uploadedFile = UploadedFile(filename = file.originalFilename, inputStreamSource = { file.inputStream })
        return trackService.upload(channel, uploadedFile)
    }

    @PreAuthorize(TRACK_AND_CHANNEL_PRE_AUTH)
    @GetMapping("{trackId}")
    fun get(@PathVariable("trackId") track: Track, @PathVariable("channelId") channel: Channel): Track {
        return track
    }

    @PreAuthorize(TRACK_AND_CHANNEL_PRE_AUTH)
    @DeleteMapping("{trackId}")
    fun delete(@PathVariable("trackId") track: Track, @PathVariable("channelId") channel: Channel) {
        trackService.delete(track)
    }

}
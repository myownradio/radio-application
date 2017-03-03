package com.radioteria.web.controller.public

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.service.NowPlayingService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class NowPlayingController(val nowPlayingService: NowPlayingService) {
    @GetMapping("/api/public/channel/{id}/now")
    fun nowPlaying(@PathVariable("id") channel: Channel): NowPlayingService.NowPlaying {
        return nowPlayingService.getNowPlaying(channel)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): Map<String, String?> {
        return mapOf("message" to e.message)
    }
}
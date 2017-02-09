package com.radioteria.web.controller

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.web.request.NewChannelRequest
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.validation.Valid

@RestController
@ExposesResourceFor(Channel::class)
@RequestMapping("/api/channel")
class ChannelController : AuthenticatedController() {
    @Resource
    lateinit var channelRepository: ChannelRepository

    @ResponseBody
    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(@Valid @RequestBody request: NewChannelRequest): Channel {
        val channel = Channel(
                name = request.name,
                user = authenticatedUser
        )

        channelRepository.save(channel)

        return channel
    }
}
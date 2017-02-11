package com.radioteria.web.controller

import com.radioteria.auth.UserEntityDetails
import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.web.request.ChannelRequest
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@ExposesResourceFor(Channel::class)
@Secured("ROLE_USER")
@RequestMapping("/api/channel")
@RestController
class ChannelController(val channelRepository: ChannelRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(
            @Valid @RequestBody request: ChannelRequest,
            @AuthenticationPrincipal principal: UserEntityDetails
    ): Channel {
        val channel = Channel(user = principal.user)
        request.fillChannel(channel)
        channelRepository.save(channel)

        return channel
    }

    @PreAuthorize("#channel.belongsTo(principal.user)")
    @RequestMapping("{id}", method = arrayOf(RequestMethod.GET))
    fun get(@PathVariable("id") channel: Channel): Channel {
        return channel
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun list(@AuthenticationPrincipal principal: UserEntityDetails): List<Channel> {
        return channelRepository.findAllByUserId(principal.user.id!!)
    }

    @PreAuthorize("#channel.belongsTo(principal.user)")
    @RequestMapping("{id}", method = arrayOf(RequestMethod.PUT))
    fun save(
            @PathVariable("id") channel: Channel,
            @Valid @RequestBody request: ChannelRequest
    ): Channel {
        request.fillChannel(channel)

        channelRepository.save(channel)

        return channel
    }

    @PreAuthorize("#channel.belongsTo(principal.user)")
    @RequestMapping("{id}", method = arrayOf(RequestMethod.DELETE))
    fun delete(@PathVariable("id") channel: Channel) {
        channelRepository.delete(channel)
    }
}
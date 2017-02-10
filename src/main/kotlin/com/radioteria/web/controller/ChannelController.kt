package com.radioteria.web.controller

import com.radioteria.auth.NoPermissionException
import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.web.request.ChannelRequest
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun create(@Valid @RequestBody request: ChannelRequest): Channel {
        val channel = Channel(user = authenticatedUser)
        request.fillChannel(channel)
        channelRepository.save(channel)

        return channel
    }

    @RequestMapping("{id}", method = arrayOf(RequestMethod.GET))
    fun get(@PathVariable("id") channel: Channel): ResponseEntity<Channel> {
        if (!channel.belongsTo(authenticatedUser)) {
            throwAccessDenied()
        }

        return ResponseEntity.ok(channel)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun list(): List<Channel> {
        return channelRepository.findAllByUserId(authenticatedUser.id!!)
    }

    @RequestMapping("{id}", method = arrayOf(RequestMethod.PUT))
    fun save(
            @PathVariable("id") channel: Channel,
            @Valid @RequestBody request: ChannelRequest
    ): ResponseEntity<Channel> {
        if (!channel.belongsTo(authenticatedUser)) {
            throwAccessDenied()
        }

        request.fillChannel(channel)

        channelRepository.save(channel)

        return ResponseEntity.ok(channel)
    }

    @RequestMapping("{id}", method = arrayOf(RequestMethod.DELETE))
    fun delete(@PathVariable("id") channel: Channel) {
        if (!channel.belongsTo(authenticatedUser)) {
            throwAccessDenied()
        }

        channelRepository.delete(channel)
    }

    fun throwAccessDenied() {
        throw NoPermissionException("You do not have permissions to access this resource.")
    }
}
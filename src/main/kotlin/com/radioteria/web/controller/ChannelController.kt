package com.radioteria.web.controller

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.web.form.NewChannelForm
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.validation.Valid

@RestController
@ExposesResourceFor(Channel::class)
@RequestMapping("/api/channel")
class ChannelController : AuthenticatedController() {
    @Resource
    lateinit var channelRepository: ChannelRepository

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(@RequestBody @Valid newChannelForm: NewChannelForm): ResponseEntity<Channel> {
        val channel = Channel(
                name = newChannelForm.name,
                user = authenticatedUser
        )
        val savedChannel = channelRepository.save(channel)
        return ResponseEntity.ok(savedChannel)
    }
}
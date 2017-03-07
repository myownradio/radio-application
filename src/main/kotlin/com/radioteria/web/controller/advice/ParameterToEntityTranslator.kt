package com.radioteria.web.controller.advice

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.entity.Track
import com.radioteria.domain.repository.ChannelRepository
import com.radioteria.domain.repository.TrackRepository
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestController
import java.beans.PropertyEditorSupport
import javax.persistence.EntityNotFoundException

@ControllerAdvice(annotations = arrayOf(RestController::class))
class ParameterToEntityTranslator(
        val channelRepository: ChannelRepository,
        val trackRepository: TrackRepository
) {

    @InitBinder
    fun initBinder(webDataBinder: WebDataBinder) {
        webDataBinder.registerCustomEditor(Channel::class.java, object : PropertyEditorSupport() {
            override fun setAsText(id: String?) {
                value = channelRepository.findOne(id?.toLong()) ?:
                        throw EntityNotFoundException("Channel with id $id does not exist.")
            }
        })

        webDataBinder.registerCustomEditor(Track::class.java, object : PropertyEditorSupport() {
            override fun setAsText(id: String?) {
                value = trackRepository.findOne(id?.toLong()) ?:
                        throw EntityNotFoundException("Track with id $id does not exist.")
            }
        })
    }

}
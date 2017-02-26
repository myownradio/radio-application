package com.radioteria.web.controller.advice

import com.radioteria.domain.entity.Channel
import com.radioteria.domain.repository.ChannelRepository
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestController
import java.beans.PropertyEditorSupport
import javax.persistence.EntityNotFoundException

@ControllerAdvice(annotations = arrayOf(RestController::class))
class ParameterToEntityTranslator(
        val channelRepository: ChannelRepository
) {

    @InitBinder
    fun initBinder(webDataBinder: WebDataBinder) {
        webDataBinder.registerCustomEditor(Channel::class.java, object : PropertyEditorSupport() {
            override fun setAsText(id: String?) {
                value = channelRepository.findOne(id?.toLong()) ?:
                        throw EntityNotFoundException("Channel with id $id does not exist.")
            }

            override fun getAsText(): String {
                return (value as Channel).id.toString()
            }
        })
    }

}
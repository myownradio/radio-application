package com.radioteria.web.controller.advice

import com.radioteria.domain.service.exception.ChannelStateException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException

@ControllerAdvice(annotations = arrayOf(RestController::class))
class ControllerExceptionTranslator {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): Map<String, String?> {
        return transformException(e)
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChannelStateException::class)
    fun handleChannelStateException(e: ChannelStateException): Map<String, String?> {
        return transformException(e)
    }

    fun transformException(e: Exception): Map<String, String?> {
        return mapOf("message" to e.message)
    }

}
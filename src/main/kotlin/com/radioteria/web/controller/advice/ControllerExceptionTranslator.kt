package com.radioteria.web.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException

@ControllerAdvice(annotations = arrayOf(RestController::class))
class ControllerExceptionTranslator {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleException(e: EntityNotFoundException): Exception {
        return e
    }
}
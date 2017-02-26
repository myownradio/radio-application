package com.radioteria.service.core

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidAudioFileException : Exception("Uploaded audio file is not valid.")
package com.radioteria.auth

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class NoPermissionException(message: String) : Exception(message)
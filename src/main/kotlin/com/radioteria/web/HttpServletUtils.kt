package com.radioteria.web

import com.fasterxml.jackson.databind.ObjectMapper
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.sendOk() {
    ObjectMapper().writeValue(outputStream, mapOf("message" to "OK"))
}

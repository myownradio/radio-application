package com.radioteria.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

fun MockHttpServletRequestBuilder.withBody(any: Any?): MockHttpServletRequestBuilder {
    return contentType("application/json")
            .content(ObjectMapper().writeValueAsBytes(any))
}
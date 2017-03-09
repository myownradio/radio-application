package com.radioteria.controller.auth

import com.radioteria.controller.AbstractControllerTest
import com.radioteria.util.withBody
import com.radioteria.web.request.RegistrationRequest
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

class RegistrationControllerTest : AbstractControllerTest() {
    @Test
    fun whenFormIsEmpty() {
        mvc.perform(post("/api/auth/register")
                .withBody(RegistrationRequest("", "", "")))

                .andDo(print())
    }
}
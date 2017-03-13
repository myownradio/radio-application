package com.radioteria.controller

import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class HomeControllerTest : AbstractControllerTest() {
    @Test
    fun index() {
        mvc.perform(get("/"))
                .andExpect(view().name("index"))
    }
}
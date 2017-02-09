package com.radioteria.controller

import org.junit.Test
import org.springframework.security.test.context.support.WithMockUser

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class HomeControllerTest : AbstractControllerTest() {

    @Test
    fun mustBeDeniedForGuestUser() {
        mvc.perform(get("/"))
                .andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(roles = arrayOf("USER"))
    fun mustBeOkForAuthenticatedUser() {
        mvc.perform(get("/"))
                .andExpect(status().isNotFound)
    }

}
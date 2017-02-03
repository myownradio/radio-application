package com.radioteria.controllers

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@WebMvcTest
class HomeControllerTest {

    @Autowired lateinit var mvc: MockMvc

    @Test
    fun mustBeDeniedForGuestUser() {
        mvc.perform(get("/"))
                .andExpect(status().isUnauthorized)
    }

    @Test
    @WithMockUser(roles = arrayOf("USER"))
    fun mustBeOkForAuthenticatedUser() {
        mvc.perform(get("/"))
                .andExpect(status().isNotFound)
    }

}
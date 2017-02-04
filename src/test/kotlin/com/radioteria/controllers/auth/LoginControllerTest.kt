package com.radioteria.controllers.auth

import com.radioteria.controllers.AbstractControllerTest
import org.junit.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LoginControllerTest : AbstractControllerTest() {

    companion object {
        const val API_LOGIN_ENDPOINT = "/api/auth/login"

        const val WRONG_EMAIL = "wrong@email.com"
        const val WRONG_PASSWORD = "sOmEwRoNgPaSsWoRd"

        const val CORRECT_EMAIL = "mat@foo.com"
        const val CORRECT_PASSWORD = "LHzxdqkRPGKPdcY6"
    }

    @Test
    fun loginWithoutCredentials() {
        val request = post(API_LOGIN_ENDPOINT)
                .with(csrf())

        mvc.perform(request).andExpect(status().isUnauthorized)
    }

    @Test
    fun loginWithWrongCredentials() {
        val request = post(API_LOGIN_ENDPOINT)
                .with(csrf())
                .param("email", WRONG_EMAIL)
                .param("password", WRONG_PASSWORD)

        mvc.perform(request)
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun loginWithCorrectCredentials() {
        val request = post(API_LOGIN_ENDPOINT)
                .with(csrf())
                .param("email", CORRECT_EMAIL)
                .param("password", CORRECT_PASSWORD)

        mvc.perform(request)
                .andExpect(status().isOk)
    }

    @Test
    fun foo() {
        val request = post("/foo")
                .with(csrf())

        mvc.perform(request)
                .andExpect(status().isUnauthorized)
    }
}
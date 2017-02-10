package com.radioteria.controller.auth

import com.radioteria.controller.AbstractControllerTest
import org.junit.Test
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LoginControllerTest : AbstractControllerTest() {

    companion object {
        const val API_LOGIN_ENDPOINT = "/api/auth/login"

        const val WRONG_EMAIL = "wrong@mail.com"
        const val WRONG_PASSWORD = "sOmEwRoNgPaSsWoRd"

        const val CORRECT_EMAIL = "user@mail.com"
        const val CORRECT_PASSWORD = "pwd"

        const val INACTIVE_EMAIL = "inactive@mail.com"
        const val INACTIVE_PASSWORD = "pwd"
    }

    @Test
    fun loginWithoutCredentials() {
        val request = post(API_LOGIN_ENDPOINT)
                .with(csrf())

        mvc.perform(request)
                .andExpect(status().isUnauthorized)
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
    fun loginWhenInactive() {
        val request = post(API_LOGIN_ENDPOINT)
                .with(csrf())
                .param("email", INACTIVE_EMAIL)
                .param("password", INACTIVE_PASSWORD)

        mvc.perform(request)
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun foo() {
        val request = post("/foo")
                .with(csrf())

        mvc.perform(request)
                .andExpect(status().isUnauthorized)
    }
}
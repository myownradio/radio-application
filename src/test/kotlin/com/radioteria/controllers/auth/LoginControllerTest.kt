package com.radioteria.controllers.auth

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Ignore
@RunWith(SpringRunner::class)
@WebMvcTest
class LoginControllerTest {

    companion object {
        const val API_LOGIN_ENDPOINT = "/api/auth/login"

        const val WRONG_EMAIL = "wrong@email.com"
        const val WRONG_PASSWORD = "sOmEwRoNgPaSsWoRd"

        const val CORRECT_EMAIL = "mat@foo.com"
        const val CORRECT_PASSWORD = "LHzxdqkRPGKPdcY6"
    }

    @Autowired lateinit var mvc: MockMvc

    @Test
    fun loginWithoutCredentials() {
        val request = post(API_LOGIN_ENDPOINT)

        mvc.perform(request).andExpect(status().isBadRequest)
    }

    @Test
    fun loginWithWrongCredentials() {
        val request = post(API_LOGIN_ENDPOINT)
                .param("email", WRONG_EMAIL)
                .param("password", WRONG_PASSWORD)

        mvc.perform(request).andExpect(status().isUnauthorized)
    }

    @Test
    fun loginWithCorrectCredentials() {
        val request = post(API_LOGIN_ENDPOINT)
                .param("email", CORRECT_EMAIL)
                .param("password", CORRECT_PASSWORD)

        mvc.perform(request).andExpect(status().isOk)
    }
}
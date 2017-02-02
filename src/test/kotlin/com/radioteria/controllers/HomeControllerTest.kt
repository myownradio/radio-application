package com.radioteria.controllers

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@SpringBootTest
@WebMvcTest
class HomeControllerTest {
    @Autowired
    lateinit var applicationContext: WebApplicationContext

    val mockMvc: MockMvc by lazy { MockMvcBuilders.webAppContextSetup(applicationContext).build() }

    @Test
    fun test() {

    }
}
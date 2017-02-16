package com.radioteria.service

import com.radioteria.service.shell.BinaryLocator
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*

@RunWith(SpringRunner::class)
@SpringBootTest
class BinaryLocatorServiceTest {

    @Autowired
    lateinit var binaryLocator: BinaryLocator

    @Test
    fun locateEcho() {
        val result = binaryLocator.locate("echo")
        assertThat(result, endsWith("echo"))
    }

}
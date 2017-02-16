package com.radioteria.service

import com.radioteria.service.shell.BinaryLocator
import com.radioteria.service.shell.ShellBinaryLocator
import org.junit.Test

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*

// todo: replace this stub by real ffprobe test
class BinaryLocatorServiceTest {

    val binaryLocator: BinaryLocator = ShellBinaryLocator()

    @Test
    fun locateEcho() {
        val result = binaryLocator.locate("echo")
        assertThat(result, endsWith("echo"))
    }

}
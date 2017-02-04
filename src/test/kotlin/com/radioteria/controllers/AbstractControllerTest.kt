package com.radioteria.controllers

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.TestExecutionListeners.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(DbUnitTestExecutionListener::class, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@DatabaseSetup("classpath:datasets/user-table.xml")
abstract class AbstractControllerTest {
    @Autowired lateinit var mvc: MockMvc
}

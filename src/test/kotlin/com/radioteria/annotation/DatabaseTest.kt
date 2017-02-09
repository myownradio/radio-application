package com.radioteria.annotation

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import org.springframework.test.context.TestExecutionListeners

@DatabaseSetup("classpath:dataset.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE)
@TestExecutionListeners(DbUnitTestExecutionListener::class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
annotation class DatabaseTest

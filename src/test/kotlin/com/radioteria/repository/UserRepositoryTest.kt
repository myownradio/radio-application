package com.radioteria.repository

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import com.radioteria.domain.entity.User
import com.radioteria.domain.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.junit.Ignore
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests


@Ignore
@TestExecutionListeners(DbUnitTestExecutionListener::class)
@DatabaseSetup("classpath:datasets/user-table.xml")
@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTest : AbstractTransactionalJUnit4SpringContextTests() {

    @Autowired lateinit var repository: UserRepository

    @Test
    fun findUserById() {
        val user = repository.findOne(1L)!!
        assertThat(user.email, equalTo("sam@foo.com"))
        assertThat(user.role, equalTo(User.Role.ADMIN))
    }

    @Test
    fun findUserByEmail() {
        val user = repository.findByEmail("sam@foo.com")!!
        assertThat(user.email, equalTo("sam@foo.com"))
        assertThat(user.role, equalTo(User.Role.ADMIN))
    }
}
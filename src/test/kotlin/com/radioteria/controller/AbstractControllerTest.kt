package com.radioteria.controller

import com.radioteria.annotation.DatabaseTest
import com.radioteria.annotation.InjectUserDetails
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@DatabaseTest
abstract class AbstractControllerTest {
    @Autowired
    lateinit var mvc: MockMvc

    @field:InjectUserDetails("user@example.com")
    lateinit var userDetails: UserDetails
}

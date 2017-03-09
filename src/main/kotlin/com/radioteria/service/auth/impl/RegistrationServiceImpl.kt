package com.radioteria.service.auth.impl

import com.radioteria.domain.entity.User
import com.radioteria.domain.repository.UserRepository
import com.radioteria.service.auth.RegistrationService
import com.radioteria.service.auth.event.UserRegisteredEvent
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class RegistrationServiceImpl(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder,
        val applicationEventMulticaster: ApplicationEventMulticaster
) : RegistrationService {

    @Transactional
    override fun register(email: String, password: String, name: String): User {
        val encodedPassword = passwordEncoder.encode(password)

        val user = User(email = email, password = encodedPassword, name = name)

        userRepository.save(user)

        publishUserRegisteredEvent(user)

        return user
    }

    private fun publishUserRegisteredEvent(user: User) {
        applicationEventMulticaster.multicastEvent(UserRegisteredEvent(this, user))
    }

}
package com.radioteria.service.auth.event

import com.radioteria.domain.entity.User
import org.springframework.context.ApplicationEvent

class UserRegisteredEvent(target: Any, val user: User) : ApplicationEvent(target)
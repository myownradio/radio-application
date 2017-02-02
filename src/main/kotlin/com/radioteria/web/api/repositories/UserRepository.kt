package com.radioteria.web.api.repositories

import com.radioteria.web.api.domain.User
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : PagingAndSortingRepository<User, Long>
package com.radioteria.web.api.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class User(
        @Id
        @GeneratedValue
        var id: Long? = null,
        var email: String = "",
        var name: String = ""
)
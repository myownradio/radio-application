package com.radioteria.domain.entity

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        var id: Long? = null,

        @Column(name = "email", unique = true, nullable = false)
        var email: String = "",

        @Column(name = "name", nullable = false)
        var name: String = "",

        @Column(name = "password", nullable = false)
        var password: String = "",

        @Column(name = "status", nullable = false)
        @Enumerated(EnumType.STRING)
        val status: Status = Status.INACTIVE,

        @Column(name = "role", nullable = false)
        @Enumerated(EnumType.STRING)
        val role: Role = Role.USER
) {
    enum class Status { INACTIVE, ACTIVE, SUSPENDED, BANNED }
    enum class Role { USER, ADMIN }
}

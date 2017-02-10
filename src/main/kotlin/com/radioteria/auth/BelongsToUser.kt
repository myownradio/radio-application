package com.radioteria.auth

import com.radioteria.domain.entity.User

interface BelongsToUser {
    fun belongsTo(user: User): Boolean
}
package com.radioteria.domain

import com.radioteria.domain.entity.Track


fun List<Track>.ids(): List<Long> {
    return map { it.id!! }
}

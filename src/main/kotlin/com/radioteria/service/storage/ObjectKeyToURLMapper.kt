package com.radioteria.service.storage

import java.net.URL

interface ObjectKeyToURLMapper {
    fun map(objectKey: String): URL
}

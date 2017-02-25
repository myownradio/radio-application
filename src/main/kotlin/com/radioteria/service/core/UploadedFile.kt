package com.radioteria.service.core

import java.io.InputStream

data class UploadedFile(val filename: String, private val inputStreamSource: () -> InputStream) {
    val inputStream: InputStream get() = inputStreamSource.invoke()
}
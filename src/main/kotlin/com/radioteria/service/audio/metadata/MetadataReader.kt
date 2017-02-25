package com.radioteria.service.audio.metadata

import java.io.File
import java.io.InputStream

interface MetadataReader {
    fun read(file: String): Metadata
    fun read(file: File): Metadata
    fun read(inputStream: InputStream): Metadata
}
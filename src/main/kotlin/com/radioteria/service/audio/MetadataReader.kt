package com.radioteria.service.audio

import java.io.File

interface MetadataReader {
    fun read(file: String): Metadata
    fun read(file: File): Metadata
}
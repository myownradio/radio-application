package com.radioteria.service.audio

interface MetadataReader {
    fun read(filename: String): Metadata
}
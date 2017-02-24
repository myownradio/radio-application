package com.radioteria.service.storage

import java.io.InputStream

interface ObjectStorage {

    class Object(val key: String, val length: Long, val metadata: Metadata, val objectContent: () -> InputStream) {
        inline fun <R> withContent(block: (InputStream) -> R): R {
            return objectContent.invoke().use { block.invoke(it) }
        }
    }

    fun has(key: String): Boolean

    fun get(key: String): Object

    fun delete(key: String)

    fun put(key: String, inputStream: InputStream, metadata: Metadata)

}
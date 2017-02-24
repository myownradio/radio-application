package com.radioteria.service.storage

import java.io.InputStream
import java.net.URL
import java.util.*

interface ObjectStorage {

    class Object(
            val key: String,
            val metadata: Properties,
            val length: Long,
            val contentSupplier: () -> InputStream
    ) {
        inline fun <R> withContent(block: (InputStream) -> R): R {
            return contentSupplier.invoke().use { block.invoke(it) }
        }
    }

    fun has(key: String): Boolean

    fun get(key: String): Object

    fun delete(key: String)

    fun create(key: String, inputStream: InputStream, metadata: Properties)

}
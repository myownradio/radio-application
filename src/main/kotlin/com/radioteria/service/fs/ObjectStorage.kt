package com.radioteria.service.fs

import java.io.InputStream
import java.net.URL
import java.util.*

interface ObjectStorage {

    class Object(
            val key: String,
            val metadata: Properties,
            val length: Long,
            val url: URL,
            val streamProvider: () -> InputStream
    ) {
        inline fun <R> use(block: (InputStream) -> R): R {
            return streamProvider.invoke().use { block.invoke(it) }
        }
    }

    fun has(key: String): Boolean

    fun get(key: String): Object

    fun delete(key: String)

    fun create(key: String, inputStream: InputStream, metadata: Properties)

    fun getURL(key: String): URL

}
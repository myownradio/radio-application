package com.radioteria.service.fs

import java.io.InputStream
import java.net.URL

interface FileSystem {

    data class FileItem(
            val id: String,
            val contentType: String,
            val length: Long,
            val fileUrl: URL,
            val streamProvider: () -> InputStream
    ) {
        inline fun <R> use(block: (InputStream) -> R): R {
            return streamProvider.invoke().use { block.invoke(it) }
        }
    }

    fun has(id: String): Boolean

    fun get(id: String): FileItem?

    fun delete(id: String)

    fun create(id: String, dataStream: InputStream, contentType: String)

}
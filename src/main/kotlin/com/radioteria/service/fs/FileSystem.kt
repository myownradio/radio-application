package com.radioteria.service.fs

import java.io.InputStream
import java.net.URL

interface FileSystem {

    data class FileItem(
            val id: String,
            val contentType: String,
            val length: String,
            val fileUrl: URL
    )

    fun has(id: String): Boolean

    fun get(id: String): FileItem?

    fun delete(id: String)

    fun create(id: String, dataStream: InputStream)

}
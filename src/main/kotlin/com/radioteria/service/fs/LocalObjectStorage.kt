package com.radioteria.service.fs

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.*


class LocalObjectStorage(val root: File, val mapFileIdToURL: (String) -> URL) : ObjectStorage {

    override fun has(key: String): Boolean {
        return getContentFile(key).exists() && getMetadataFile(key).exists()
    }

    override fun get(key: String): ObjectStorage.Object {
        val metadata = readMetadata(key)
        val contentFile = getContentFile(key)
        val contentSize: Long = contentFile.length()

        return ObjectStorage.Object(
                key,
                metadata,
                contentSize,
                getURL(key),
                { FileInputStream(contentFile) }
        )
    }

    override fun delete(key: String) {
        getMetadataFile(key).delete()
        getContentFile(key).delete()
    }

    override fun create(key: String, inputStream: InputStream, metadata: Properties) {
        val outputStream = FileOutputStream(getContentFile(key))

        outputStream.use { inputStream.copyTo(it) }

        writeMetadata(key, metadata)
    }

    override fun getURL(key: String): URL {
        return mapFileIdToURL(key)
    }

    private fun getContentFile(key: String): File {
        val filename = keyToFileName(key)
        return File("$root${File.separator}$filename.content")
    }

    private fun getMetadataFile(key: String): File {
        val filename = keyToFileName(key)
        return File("$root${File.separator}$filename.metadata")
    }

    private fun readMetadata(key: String): Properties {
        val properties = Properties()
        FileInputStream(getMetadataFile(key)).use { properties.load(it) }
        return properties
    }

    private fun writeMetadata(key: String, metadata: Properties) {
        FileOutputStream(getMetadataFile(key)).use { metadata.store(it, null) }
    }

    private fun keyToFileName(key: String): String {
        return key.toCharArray().map {
            if (it == File.separatorChar) { return "." }
            if (it == '.') { return ".." }
            it.toString()
        }.fold("", { a, b -> "$a$b" })
    }

}
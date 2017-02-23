package com.radioteria.service.storage

import com.radioteria.config.spring.logging.Logging
import java.io.*
import java.net.URL
import java.util.*

@Logging
class LocalObjectStorage(val root: File, val fileIdToURLMapper: (String) -> URL) : ObjectStorage {

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
        return fileIdToURLMapper(key)
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
        return key.replace(File.separatorChar, '.')
    }

}
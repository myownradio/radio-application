package com.radioteria.service.fs

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.*


class LocalFileSystem(val root: String, val mapIdToURL: (String) -> URL) : FileSystem {

    override fun has(id: String): Boolean {
        return getContentFile(id).exists() && getMetadataFile(id).exists()
    }

    override fun get(id: String): FileSystem.FileItem {
        val metadata = readMetadata(id)
        val contentFile = getContentFile(id)

        val contentType: String = metadata.getProperty("Content-Type") ?: "application/octet-stream"
        val contentSize: Long = contentFile.length()

        return FileSystem.FileItem(
                id,
                contentType,
                contentSize,
                mapIdToURL(id),
                { FileInputStream(contentFile) }
        )
    }

    override fun delete(id: String) {
        getMetadataFile(id).delete()
        getContentFile(id).delete()
    }

    override fun create(id: String, dataStream: InputStream, contentType: String) {
        val metadata = mapOf("Content-Type" to contentType).toProperties()
        val outputStream = FileOutputStream(getContentFile(id))

        outputStream.use { dataStream.copyTo(it) }

        writeMetadata(id, metadata)
    }

    private fun getContentFile(id: String): File {
        return File("$root${File.separator}$id.content")
    }

    private fun getMetadataFile(id: String): File {
        return File("$root${File.separator}$id.metadata")
    }

    private fun readMetadata(id: String): Properties {
        val properties = Properties()
        FileInputStream(getMetadataFile(id)).use { properties.load(it) }
        return properties
    }

    private fun writeMetadata(id: String, metadata: Properties) {
        FileOutputStream(getMetadataFile(id)).use { metadata.store(it, null) }
    }

}
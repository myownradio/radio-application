package com.radioteria.service.storage

import com.radioteria.config.spring.logging.Logging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.*
import java.net.URL
import java.util.*

@Logging
@Service
class LocalObjectStorage(
        @Value("\${radioteria.storage.local.dir}") val root: File,
        val objectKeyToURLMapper: ObjectKeyToURLMapper
) : ObjectStorage {

    init {
        root.exists() || root.mkdirs() || throw IOException("Directory '$root' could not be created.")
    }

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
        val contentFile = getContentFile(key)

        createParentDirs(contentFile)

        FileOutputStream(contentFile)
                .use { inputStream.copyTo(it) }

        writeMetadata(key, metadata)
    }

    private fun createParentDirs(contentFile: File) {
        if (!contentFile.parentFile.exists()) {
            contentFile.parentFile.mkdirs()
        }
    }

    override fun getURL(key: String): URL {
        return objectKeyToURLMapper.map(key)
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
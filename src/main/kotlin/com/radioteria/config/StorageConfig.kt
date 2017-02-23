package com.radioteria.config

import com.radioteria.service.storage.LocalObjectStorage
import com.radioteria.service.storage.ObjectStorage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.io.File
import java.net.URL

@Configuration
class StorageConfig {

    @Bean
    fun localTempObjectStorage(): ObjectStorage {
        val tmpdir = getContentDirectory()

        createDirectoryIfNotExist(tmpdir)

        return LocalObjectStorage(tmpdir, { id -> URL("file:$tmpdir/$id") })
    }

    fun getContentDirectory(): File {
        val tmpdir = System.getenv("java.io.tmpdir")
        return File(tmpdir, "radioteria/content")
    }

    fun createDirectoryIfNotExist(file: File) {
        if (!file.exists()) {
            file.mkdirs()
        }
    }

}
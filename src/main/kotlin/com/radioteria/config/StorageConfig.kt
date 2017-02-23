package com.radioteria.config

import com.radioteria.config.spring.logging.Logging
import com.radioteria.service.storage.LocalObjectStorage
import com.radioteria.service.storage.ObjectStorage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.net.URL

@Configuration
class StorageConfig {

    @Bean
    fun localTempObjectStorage(): ObjectStorage {
        val tmpdir = getContentDirectory()

        createDirectoryIfNotExist(tmpdir)

        return LocalObjectStorage(
                tmpdir,
                { id: String -> URL("file:$tmpdir/$id") }
        )
    }

    fun getContentDirectory(): File {
        val tmpdir = System.getProperty("java.io.tmpdir")
        return File(tmpdir, "radioteria.service/content")
    }

    fun createDirectoryIfNotExist(file: File) {
        if (!file.exists()) { file.mkdirs() }
    }

}
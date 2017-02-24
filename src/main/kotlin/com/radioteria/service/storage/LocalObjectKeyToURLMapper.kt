package com.radioteria.service.storage

import com.radioteria.config.spring.logging.Logging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.net.URL

@Logging
@Service
class LocalObjectKeyToURLMapper(@Value("\${radioteria.storage.local.dir}") val root: File) : ObjectKeyToURLMapper {
    override fun map(objectKey: String): URL = URL("file:$root/$objectKey")
}

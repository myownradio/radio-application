package com.radioteria.web.controller

import com.radioteria.service.storage.ObjectStorage
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*

//@Secured("ROLE_USER")
@RequestMapping("/api/upload")
@RestController
class UploadController(val objectStorage: ObjectStorage) {

    @PostMapping
    fun upload(@RequestPart(value = "file", required = true) file: MultipartFile): String {
        val metadata = Properties()
        metadata.setProperty("ContentType", file.contentType)

        file.inputStream.use { objectStorage.create(file.originalFilename, it, metadata) }

        return file.originalFilename
    }

}
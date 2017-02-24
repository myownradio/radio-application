package com.radioteria.web.controller

import com.radioteria.service.storage.Metadata
import com.radioteria.service.storage.ObjectStorage
import com.radioteria.util.io.sha1
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

//@Secured("ROLE_USER")
@RequestMapping("/api/upload")
@RestController
class UploadController(val objectStorage: ObjectStorage) {

    @PostMapping
    fun upload(@RequestPart(value = "file", required = true) file: MultipartFile): String {
        val metadata = Metadata(contentType = file.contentType)

        val sha1 = file.inputStream.use(::sha1)

        file.inputStream.use { objectStorage.put(file.originalFilename, it, metadata) }

        return sha1
    }

}
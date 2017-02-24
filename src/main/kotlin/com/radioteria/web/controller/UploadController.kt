package com.radioteria.web.controller

import com.radioteria.domain.entity.File
import com.radioteria.service.fs.FileService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

//@Secured("ROLE_USER")
@RequestMapping("/api/upload")
@RestController
class UploadController(val fileService: FileService) {

    @PostMapping
    fun upload(@RequestPart(value = "file", required = true) file: MultipartFile): File {
        return fileService.put(file.originalFilename, { file.inputStream })
    }

}

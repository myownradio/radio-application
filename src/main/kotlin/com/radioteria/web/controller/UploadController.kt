package com.radioteria.web.controller

import com.radioteria.domain.entity.File
import com.radioteria.service.fs.FileService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

//@Secured("ROLE_USER")
@RequestMapping("/api/upload")
@RestController
class UploadController(val fileService: FileService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun upload(@RequestPart(value = "file", required = true) file: MultipartFile): File {
        return fileService.put(file.originalFilename, { file.inputStream })
    }

}

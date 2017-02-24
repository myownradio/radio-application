package com.radioteria.domain.repository

import com.radioteria.domain.entity.Blob
import com.radioteria.domain.entity.File
import org.springframework.data.repository.Repository

interface FileRepository : Repository<File, Long> {
    fun findOne(id: Long?): File?
    fun save(file: File)
    fun delete(file: File)
    fun findAllByBlob(blob: Blob): List<File>
}

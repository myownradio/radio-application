package com.radioteria.service.fs

import com.radioteria.domain.entity.File
import com.radioteria.service.storage.ObjectStorage
import java.io.InputStream

interface FileService {

    fun put(filename: String, inputStreamSupplier: () -> InputStream): File

    fun get(file: File): ObjectStorage.Object

    fun delete(file: File)

}
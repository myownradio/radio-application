package com.radioteria.service.fs

import com.radioteria.domain.entity.Blob
import com.radioteria.domain.entity.File
import com.radioteria.domain.repository.BlobRepository
import com.radioteria.domain.repository.FileRepository
import com.radioteria.orElse
import com.radioteria.service.storage.Metadata
import com.radioteria.service.storage.ObjectStorage
import com.radioteria.util.io.sha1
import net.sf.jmimemagic.Magic
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.InputStream
import javax.transaction.Transactional

@Service
class GenericFileService(
        val blobRepository: BlobRepository,
        val fileRepository: FileRepository,
        val objectStorage: ObjectStorage
) : FileService {

    @Transactional
    override fun put(filename: String, inputStreamSupplier: () -> InputStream): File {
        val contentType = getContentType(filename)
        val sha1 = inputStreamSupplier.invoke().use(::sha1)

        val blob = blobRepository.findByHash(sha1).orElse {
            inputStreamSupplier.invoke().use { objectStorage.put(sha1, it, Metadata(contentType = contentType)) }

            val storedObject = objectStorage.get(sha1)
            val blob = Blob(
                    contentType = contentType,
                    size = storedObject.length,
                    hash = sha1,
                    fileSystem = "unused",
                    isPermanent = true
            )
            blob.apply { blobRepository.save(this) }
        }

        return createAndSaveFile(blob, filename)
    }

    @Transactional
    override fun get(file: File): ObjectStorage.Object {
        val blob = blobRepository.findById(file.blob.id!!)!!
        return objectStorage.get(blob.hash)
    }

    @Transactional
    override fun delete(file: File) {
        val fileBlob = file.blob

        fileRepository.delete(file)

        if (fileRepository.findAllByBlob(fileBlob).isEmpty()) {
            objectStorage.delete(fileBlob.hash)
            blobRepository.delete(fileBlob)
        }
    }

    private fun createAndSaveFile(blob: Blob, filename: String): File {
        return File(name = filename, blob = blob)
                .apply { fileRepository.save(this) }
    }

    private fun getContentType(filename: String): String {
        return "application/octet-stream"
    }

}
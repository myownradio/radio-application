package com.radioteria.service.fs

import com.peacefulbit.util.forEachChunk
import com.radioteria.domain.entity.Blob
import com.radioteria.domain.entity.File
import com.radioteria.domain.repository.BlobRepository
import com.radioteria.domain.repository.FileRepository
import com.peacefulbit.util.orElse
import com.radioteria.service.storage.Metadata
import com.radioteria.service.storage.ObjectStorage
import org.apache.commons.codec.binary.Hex
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.InputStream
import java.security.MessageDigest
import javax.transaction.Transactional

@Service
class GenericFileService(
        val blobRepository: BlobRepository,
        val fileRepository: FileRepository,
        val objectStorage: ObjectStorage,
        @Value("\${radioteria.fs.hashing.algorithm}")
        val hashingAlgorithm: String
) : FileService {

    @Transactional
    override fun put(filename: String, inputStreamSupplier: () -> InputStream): File {
        val contentType = getContentType(filename)
        val (hash, length) = inputStreamSupplier.withStream { analyzeInputStream(it) }

        val blob = blobRepository.findByHash(hash).orElse {
            Blob(contentType = contentType, size = length, hash = hash).apply {
                blobRepository.save(this)

                inputStreamSupplier.withStream {
                    objectStorage.put(mapBlobToObjectName(this), it, Metadata(contentType = contentType))
                }
            }
        }

        return createAndSaveFile(blob, filename)
    }

    @Transactional
    override fun get(file: File): ObjectStorage.Object {
        val blob = blobRepository.findOne(file.blob.id!!)!!
        return objectStorage.get(blob.hash)
    }

    @Transactional
    override fun delete(file: File) {
        val fileBlob = file.blob

        fileRepository.delete(file)

        if (fileRepository.findAllByBlob(fileBlob).isEmpty()) {
            deleteBlobAndDataObject(fileBlob)
        }
    }

    private fun deleteBlobAndDataObject(blob: Blob) {
        objectStorage.delete(blob.hash)
        blobRepository.delete(blob)
    }

    @Transactional
    override fun markPermanent(file: File) {
        file.isPermanent = true
        fileRepository.save(file)
    }

    private fun createAndSaveFile(blob: Blob, filename: String): File {
        return File(name = filename, blob = blob)
                .apply { fileRepository.save(this) }
    }

    private fun getContentType(filename: String): String {
        return "application/octet-stream"
    }

    private fun mapBlobToObjectName(blob: Blob): String {
        return "file_${blob.id}"
    }

    data class AnalyzeResult(val hash: String, val length: Long)

    private fun analyzeInputStream(inputStream: InputStream): AnalyzeResult {
        val digest = MessageDigest.getInstance(hashingAlgorithm)
        var bytesRead = 0L

        inputStream.forEachChunk { digest.update(it); bytesRead += it.size }

        val hashAsHex = String(Hex.encodeHex(digest.digest()))

        return AnalyzeResult(hashAsHex, bytesRead)
    }

    inline private fun <R> (() -> InputStream).withStream(block: (InputStream) -> R): R {
        return invoke().use(block)
    }

}
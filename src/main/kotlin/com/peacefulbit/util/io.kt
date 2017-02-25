package com.peacefulbit.util

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

fun InputStream.copyToAndClose(target: OutputStream): Long {
    return use { that -> target.use { that.copyTo(it) } }
}

inline fun <R> InputStream.useAsFile(block: (file: File) -> R): R {
    val tempFile = File.createTempFile("inputStream_", null)
    use { it.copyTo(FileOutputStream(tempFile)) }
    return block.invoke(tempFile).apply { tempFile.delete() }
}

inline fun InputStream.forEachChunk(block: (byteArray: ByteArray) -> Unit): Long {
    var bytesRead = 0L
    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
    var bytes = read(buffer)

    while (bytes > 0) {
        block.invoke(buffer.sliceArray(0..bytes))
        bytesRead += bytes
        bytes = read(buffer)
    }

    return bytesRead
}

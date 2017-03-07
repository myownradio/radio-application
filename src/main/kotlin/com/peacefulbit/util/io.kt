package com.peacefulbit.util

import java.io.*

fun InputStream.copyToAndClose(target: OutputStream): Long {
    return target.use { copyTo(it) }
}

fun InputStream.toByteArray(): ByteArray {
    return ByteArrayOutputStream().use {
        copyTo(it)
        it.toByteArray()
    }
}

inline fun <R> InputStream.useAsFile(block: (file: File) -> R): R {
    val tempFile = File.createTempFile("inputStream_", null)

    try {
        use { it.copyToAndClose(FileOutputStream(tempFile)) }
        return block.invoke(tempFile)
    } finally {
        tempFile.delete()
    }
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

inline fun <R> (() -> InputStream).withStream(block: (InputStream) -> R): R {
    return invoke().use(block)
}


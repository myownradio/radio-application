package com.radioteria.util.io

import java.io.InputStream
import java.io.OutputStream
import java.security.MessageDigest
import org.apache.commons.codec.binary.Hex


fun InputStream.copyToAndClose(target: OutputStream): Long {
    return use { that -> target.use { that.copyTo(it) } }
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

fun sha1(inputStream: InputStream): String {
    val streamDigest = MessageDigest.getInstance("sha1")

    inputStream.forEachChunk { streamDigest.update(it) }

    return String(Hex.encodeHex(streamDigest.digest()))
}
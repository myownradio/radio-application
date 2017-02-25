package com.peacefulbit.util

import java.io.InputStream
import java.security.MessageDigest
import org.apache.commons.codec.binary.Hex

fun sha1(inputStream: InputStream): String {
    val streamDigest = MessageDigest.getInstance("sha1")
    inputStream.forEachChunk { streamDigest.update(it) }
    return String(Hex.encodeHex(streamDigest.digest()))
}

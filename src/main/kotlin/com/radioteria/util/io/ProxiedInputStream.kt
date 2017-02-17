package com.radioteria.util.io

import java.io.InputStream

class ProxiedInputStream(
        private val inputStream: InputStream,
        private val preRead: () -> Unit
) : InputStream() {
    override fun skip(n: Long): Long {
        preRead.invoke()
        return inputStream.skip(n)
    }

    override fun available(): Int {
        return inputStream.available()
    }

    override fun reset() {
        inputStream.reset()
    }

    override fun close() {
        preRead.invoke()
        inputStream.close()
    }

    override fun mark(readlimit: Int) {
        inputStream.mark(readlimit)
    }

    override fun markSupported(): Boolean {
        return inputStream.markSupported()
    }

    override fun read(): Int {
        preRead.invoke()
        return inputStream.read()
    }

    override fun read(b: ByteArray?): Int {
        preRead.invoke()
        return inputStream.read(b)
    }

    override fun read(b: ByteArray?, off: Int, len: Int): Int {
        preRead.invoke()
        return inputStream.read(b, off, len)
    }

}

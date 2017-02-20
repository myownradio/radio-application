package com.radioteria.util.io

import java.io.InputStream

class CountableInputStream(private val inputStream: InputStream) : InputStream() {

    var bytes = 0L
        private set

    override fun read(): Int {
        bytes ++
        return inputStream.read()
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        val result = inputStream.read(b, off, len)
        bytes += result
        return result
    }

    override fun skip(n: Long): Long {
        return inputStream.skip(n)
    }

    override fun available(): Int {
        return inputStream.available()
    }

    override fun close() {
        inputStream.close()
    }

}
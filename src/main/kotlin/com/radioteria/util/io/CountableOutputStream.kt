package com.radioteria.util.io

import java.io.OutputStream

class CountableOutputStream(private val outputStream: OutputStream) : OutputStream() {

    var bytes = 0L
        private set

    override fun write(b: Int) {
        bytes ++
        outputStream.write(b)
    }

    override fun write(b: ByteArray) {
        bytes += b.size
        outputStream.write(b)
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        bytes += len
        outputStream.write(b, off, len)
    }

    override fun flush() {
        outputStream.flush()
    }

    override fun close() {
        outputStream.close()
    }

}
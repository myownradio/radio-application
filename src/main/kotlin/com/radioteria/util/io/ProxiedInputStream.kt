package com.radioteria.util.io

import java.io.InputStream

class ProxiedInputStream(
        private val inputStream: InputStream,
        private val doBeforeEveryRead: () -> Unit
) : InputStream() {

    override fun skip(n: Long): Long {
        doBeforeEveryRead.invoke()
        return inputStream.skip(n)
    }

    override fun available(): Int {
        return inputStream.available()
    }

    override fun close() {
        inputStream.close()
    }

    override fun read(): Int {
        doBeforeEveryRead.invoke()
        return inputStream.read()
    }

    override fun read(b: ByteArray?): Int {
        doBeforeEveryRead.invoke()
        return inputStream.read(b)
    }

    override fun read(b: ByteArray?, off: Int, len: Int): Int {
        doBeforeEveryRead.invoke()
        return inputStream.read(b, off, len)
    }

}

package com.radioteria.util.io

import java.io.OutputStream

class NullOutputStream : OutputStream() {

    override fun write(b: Int) {

    }

    override fun write(b: ByteArray) {
        super.write(b)
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        super.write(b, off, len)
    }

}
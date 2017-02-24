package com.radioteria.util.io

import java.io.InputStream
import java.io.OutputStream

fun InputStream.copyToAndClose(target: OutputStream): Long {
    return use { that -> target.use { that.copyTo(it) } }
}
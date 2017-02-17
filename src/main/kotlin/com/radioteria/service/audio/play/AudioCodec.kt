package com.radioteria.service.audio.play

import java.io.InputStream
import java.io.OutputStream

interface AudioCodec {
    fun decode(from: InputStream, to: OutputStream)
    fun encode(from: InputStream, to: OutputStream)
}
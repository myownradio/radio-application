package com.radioteria.service.audio.codec

import java.io.InputStream
import java.net.URL

interface AudioDecoder {
    fun decode(url: URL): InputStream {
        return decode(url, 0L)
    }
    fun decode(url: URL, skipMilliseconds: Long): InputStream
}

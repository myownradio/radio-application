package com.radioteria.service.storage

import com.peacefulbit.util.copyToAndClose
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

@ConditionalOnProperty("radioteria.storage", havingValue = "mock")
@Service
class MockObjectStorage : ObjectStorage {

    private val content: MutableMap<String, ObjectStorage.Object> = mutableMapOf()

    override fun has(key: String): Boolean = content.containsKey(key)

    override fun get(key: String): ObjectStorage.Object = content[key] ?: throw ObjectNotFoundException(key)

    override fun delete(key: String) {
        content.remove(key)
    }

    override fun put(key: String, inputStream: InputStream, metadata: Metadata) {
        val byteArray = ByteArrayOutputStream().use {
            inputStream.copyToAndClose(it)
            it.toByteArray()
        }
        val length = byteArray.size.toLong()
        content.put(key, ObjectStorage.Object(key, length, metadata, { ByteArrayInputStream(byteArray) }))
    }

}
package com.radioteria.service.storage

import com.peacefulbit.util.copyToAndClose
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.annotation.PreDestroy

@ConditionalOnProperty("radioteria.storage", havingValue = "temp")
@Service
class MockObjectStorage : ObjectStorage {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MockObjectStorage::class.java)
    }

    private val content: MutableMap<String, ObjectStorage.Object> = mutableMapOf()

    init {
        logger.info("Initializing mock object storage")
    }

    override fun has(key: String): Boolean {
        return content.containsKey(key).apply {
            logger.info("Checking that object with key ($key) exists ($this)")
        }
    }

    override fun get(key: String): ObjectStorage.Object {
        logger.info("Getting object (key=$key)")
        return content[key] ?: throw ObjectNotFoundException(key)
    }

    override fun delete(key: String) {
        logger.info("Deleting object (key=$key)")
        content.remove(key)
    }

    override fun put(key: String, inputStream: InputStream, metadata: Metadata) {
        val byteArray = ByteArrayOutputStream().use {
            inputStream.copyToAndClose(it)
            it.toByteArray()
        }
        val length = byteArray.size.toLong()
        logger.info("Putting object (key=$key, size=$length, metadata=$metadata)")
        content.put(key, ObjectStorage.Object(key, length, metadata, { ByteArrayInputStream(byteArray) }))
    }

    @PreDestroy
    private fun onDestroy() {
        logger.info("Destroying mock object storage (content=$content)")
    }

}
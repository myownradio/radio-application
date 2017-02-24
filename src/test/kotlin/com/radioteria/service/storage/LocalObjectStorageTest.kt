package com.radioteria.service.storage

import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class LocalObjectStorageTest {

    @Rule @JvmField val folder: TemporaryFolder = TemporaryFolder()

    lateinit var objectStorage: ObjectStorage

    @Before
    fun setup() {
        folder.create()
        objectStorage = LocalObjectStorage(folder.root)
    }

    @After
    fun after() {
        folder.delete()
    }

    @Test
    fun readAndDeleteObjectSimpleName() {
        createReadAndDeleteObject("foo.txt")
    }

    @Test
    fun readAndDeleteObjectSubName() {
        createReadAndDeleteObject("foo/bar.txt")
    }

    fun createReadAndDeleteObject(key: String) {
        val contentToSave = "Object content.".toByteArray()
        val contentType = "text/plain"
        val expectedMetadata = mapOf("Content-Type" to contentType).toProperties()

        objectStorage.create(key, ByteArrayInputStream(contentToSave), expectedMetadata)

        assertTrue(objectStorage.has(key))

        val obj = objectStorage.get(key)
        val metadata = obj.metadata

        assertEquals(contentToSave.size.toLong(), obj.length)
        assertEquals(key, obj.key)
        assertEquals(contentType, metadata.getProperty("Content-Type"))

        ByteArrayOutputStream().use { outputStream ->
            obj.withContent { inputStream ->
                inputStream.copyTo(outputStream)
            }

            assertArrayEquals(contentToSave, outputStream.toByteArray())
        }

        objectStorage.delete(key)

        assertFalse(objectStorage.has(key))
    }

    @Test(expected = FileNotFoundException::class)
    fun getWhenFileDoesNotExist() {
        objectStorage.get("other.txt")
    }

    @Test
    fun deleteWhenFileDoesNotExist() {
        objectStorage.delete("other.txt")
    }

}
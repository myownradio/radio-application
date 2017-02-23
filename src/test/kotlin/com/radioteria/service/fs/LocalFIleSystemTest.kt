package com.radioteria.service.fs

import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.net.URL

class LocalFIleSystemTest {

    @Rule
    @JvmField
    val folder: TemporaryFolder = TemporaryFolder()

    lateinit var fileSystem: FileSystem

    @Before
    fun setup() {
        folder.create()
        fileSystem = LocalFileSystem(folder.root, { URL("file:$it") })
    }

    @Test
    fun commonFlow() {
        val expectedContent = "Very large content."
        val contentType = "text/plain"
        val filename = "foo.txt"
        val contentToSave = ByteArrayInputStream(expectedContent.toByteArray())
        val expectedMetadata = mapOf("Content-Type" to contentType).toProperties()

        fileSystem.create(filename, contentToSave, expectedMetadata)

        assertTrue(fileSystem.has(filename))

        val fileItem = fileSystem.get(filename)
        val metadata = fileItem.metadata

        assertEquals(expectedContent.length.toLong(), fileItem.length)
        assertEquals(filename, fileItem.filename)
        assertEquals("file:foo.txt", fileItem.fileUrl.toString())
        assertEquals(contentType, metadata.getProperty("Content-Type"))

        fileItem.use {
            val target = ByteArrayOutputStream()
            it.copyTo(target)
            val actualContent = target.toString()

            assertEquals(expectedContent, actualContent)
        }

        fileSystem.delete(filename)

        assertFalse(fileSystem.has(filename))
    }

    @Test(expected = FileNotFoundException::class)
    fun getNonExistentFile() {
        fileSystem.get("other.txt")
    }

}
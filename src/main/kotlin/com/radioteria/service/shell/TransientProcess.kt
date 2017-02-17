package com.radioteria.service.shell

import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object TransientProcess {

    private val executorService: ExecutorService = Executors.newCachedThreadPool()

    fun run(command: List<String>, from: InputStream, to: OutputStream) {
        val process = createProcess(command.toTypedArray())
        copyAndSubmitTask(from, process.outputStream) {
            copyAvailable(process.inputStream, to)
        }
        process.destroy()
    }

    private fun copyAndSubmitTask(from: InputStream, to: OutputStream, block: () -> Unit) {
        val buffer: ByteArray = ByteArray(DEFAULT_BUFFER_SIZE)
        var bytes: Int = from.read(buffer)
        while (bytes != 0) {
            System.err.println(from.available())
//            to.write(buffer, 0, bytes)
            executorService.submit(block)
            bytes = from.read(buffer)
        }
    }

    private fun copyAvailable(inputStream: InputStream, outputStream: OutputStream) {
        val buffer: ByteArray = ByteArray(DEFAULT_BUFFER_SIZE)
        var length: Int
        while (inputStream.available() > 0) {
            length = inputStream.read(buffer)
            outputStream.write(buffer, 0, length)
        }
    }

    private fun createProcess(command: Array<String>): Process {
        return Runtime.getRuntime().exec(command)
    }

}
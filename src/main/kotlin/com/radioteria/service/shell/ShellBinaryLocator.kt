package com.radioteria.service.shell

import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.InputStreamReader

@Service
class ShellBinaryLocator : BinaryLocator {

    companion object {
        const val LOOKUP_COMMAND = "which"
    }

    override fun locate(binary: String): String {
        val lines = exec(arrayOf(LOOKUP_COMMAND, binary))

        if (lines.isNotEmpty()) {
            return lines.first()
        }

        throw BinaryLocatorException("Binary '$binary' not found.")
    }

    private fun exec(command: Array<String>): List<String> {
        val process = Runtime.getRuntime().exec(command)
        val exitStatus = process.waitFor()

        if (exitStatus != 0) {
            throw BinaryLocatorException("Command 'which' returned with exit status $exitStatus.")
        }

        return process.inputStream.use { readStreamLines(it) }
    }

    private fun readStreamLines(inputStream: InputStream) =
            InputStreamReader(inputStream)
                .use(InputStreamReader::readLines)

}
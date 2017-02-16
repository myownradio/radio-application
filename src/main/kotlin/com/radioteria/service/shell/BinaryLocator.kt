package com.radioteria.service.shell

interface BinaryLocator {
    fun locate(binary: String): String
}
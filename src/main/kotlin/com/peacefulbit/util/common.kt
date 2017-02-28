package com.peacefulbit.util

inline fun unless(expression: Boolean, block: () -> Unit): Unit {
    if (!expression) {
        block.invoke()
    }
}

inline fun <T> T?.orElse(block: () -> T): T {
    return this ?: block.invoke()
}

enum class State { STRING, DIGIT }

fun stringToStructure(string: String): List<Pair<String, Int>> {
    val result: MutableList<Pair<String, Int>> = mutableListOf()

    var previousState = State.STRING
    var stringAcc = ""
    var numberAcc = ""

    fun saveAccumulators() {
        result.add(Pair(stringAcc, numberAcc.toInt()))
        stringAcc = ""
        numberAcc = ""
    }

    string.forEachIndexed { i, symbol ->
        val newState = if (symbol.isLetter()) State.STRING else State.DIGIT
        val isLastSymbol = i == string.lastIndex

        if (newState == State.STRING && previousState == State.DIGIT) {
            saveAccumulators()
        }

        when (newState) {
            State.STRING -> stringAcc += symbol
            State.DIGIT  -> numberAcc += symbol
        }

        if (isLastSymbol) {
            saveAccumulators()
        }

        previousState = newState
    }

    return result
}

fun main(args: Array<String>) {
    println(stringToStructure("abc2edf4ee7"))
}
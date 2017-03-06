package com.peacefulbit.util

inline fun unless(expression: Boolean, block: () -> Unit): Unit {
    if (!expression) {
        block.invoke()
    }
}

inline fun <T> T?.orElse(block: () -> T): T {
    return this ?: block.invoke()
}

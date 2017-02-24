package com.radioteria

inline fun unless(expression: Boolean, block: () -> Unit): Unit {
    if (!expression) {
        block.invoke()
    }
}

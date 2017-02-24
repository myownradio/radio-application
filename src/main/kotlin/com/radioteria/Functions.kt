package com.radioteria

inline fun unless(expression: Boolean, block: () -> Unit): Unit {
    if (!expression) {
        block.invoke()
    }
}

inline fun <T> T?.orElse(block: () -> T): T {
    return if (this == null) { block.invoke() } else this
}

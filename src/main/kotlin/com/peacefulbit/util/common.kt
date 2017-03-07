package com.peacefulbit.util

inline fun <T> T?.orElse(block: () -> T): T {
    return this ?: block.invoke()
}

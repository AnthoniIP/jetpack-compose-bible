package com.ipsoft.bibliasagrada.domain.core.extension

fun <T> List<T?>.split(): List<Pair<T?, T?>> {

    val pairs = mutableListOf<Pair<T?, T?>>()
    for (i in this.indices step 2) {

        try {
            pairs.add(Pair(this[i], this[i + 1]))
        } catch (e: IndexOutOfBoundsException) {
            pairs.add(Pair(this[i], null))
        }
    }
    return pairs
}

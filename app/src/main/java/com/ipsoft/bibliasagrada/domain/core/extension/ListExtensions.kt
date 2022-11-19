package com.ipsoft.bibliasagrada.domain.core.extension

import com.ipsoft.bibliasagrada.domain.model.Verse

fun List<Verse>.split(): Pair<List<Verse>, List<Verse>> =
    Pair(this.subList(0, this.size / 2), this.subList(this.size / 2, this.size))

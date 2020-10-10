package com.smart.resources.schools_app.core.utils


// Non-nullable to Non-nullable
inline fun <I, O> mapList(input: List<I>, mapSingle: (I) -> O): List<O> {
    return input.map { mapSingle(it) }
}

// Non-nullable to Non-nullable
inline fun <I, O> mapListIndexed(input: List<I>, mapSingle: (Int, I) -> O): List<O> {
    return input.mapIndexed { index, e ->
        mapSingle(index, e)
    }
}

// Nullable to Non-nullable
inline fun <I, O> mapNullInputList(input: List<I>?, mapSingle: (I) -> O): List<O> {
    return input?.map { mapSingle(it) } ?: emptyList()
}

// Non-nullable to Nullable
inline fun <I, O> mapNullOutputList(input: List<I>, mapSingle: (I) -> O): List<O>? {
    return if (input.isEmpty()) null else input.map { mapSingle(it) }
}
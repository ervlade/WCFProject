package com.alberto.wcfproject.data

data class Routine(
    val name: String,
    val exercises: List<String>
) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "exercises" to exercises
        )
    }
}
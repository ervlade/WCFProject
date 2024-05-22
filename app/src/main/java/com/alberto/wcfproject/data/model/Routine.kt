package com.alberto.wcfproject.data.model

data class Routine(
    val id: String,
    val name: String,
    val exercises: List<String>,
) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "exercises" to exercises
        )
    }
}
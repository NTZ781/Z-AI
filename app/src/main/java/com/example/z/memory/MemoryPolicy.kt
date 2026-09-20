package com.example.z.memory

class MemoryPolicy {

    private val blockedMarkers = setOf(
        "password",
        "api_key",
        "token",
        "secret",
        "credential",
        "private_key"
    )

    fun canStore(entry: MemoryEntry): Boolean {
        if (entry.category.isBlank() ||
            entry.key.isBlank() ||
            entry.value.isBlank()
        ) {
            return false
        }

        val metadata = "${entry.category} ${entry.key}".lowercase()

        return blockedMarkers.none { marker ->
            metadata.contains(marker)
        }
    }
}

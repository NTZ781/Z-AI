package com.example.z.memory

class InMemoryMemoryRepository : MemoryRepository {

    private val entries = mutableListOf<MemoryEntry>()

    override suspend fun save(entry: MemoryEntry) {
        val index = entries.indexOfFirst { it.id == entry.id }

        if (index >= 0) {
            entries[index] = entry
        } else {
            entries.add(entry)
        }
    }

    override suspend fun get(id: String): MemoryEntry? =
        entries.firstOrNull { it.id == id }

    override suspend fun delete(id: String) {
        entries.removeAll { it.id == id }
    }

    override suspend fun getAll(): List<MemoryEntry> =
        entries.toList()

    override suspend fun search(query: String): List<MemoryEntry> {
        val normalized = query.trim().lowercase()

        if (normalized.isEmpty()) {
            return emptyList()
        }

        return entries.filter {
            it.category.lowercase().contains(normalized) ||
            it.key.lowercase().contains(normalized) ||
            it.value.lowercase().contains(normalized)
        }
    }

    override suspend fun clear() {
        entries.clear()
    }
}

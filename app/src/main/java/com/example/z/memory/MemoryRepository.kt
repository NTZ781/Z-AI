package com.example.z.memory

interface MemoryRepository {
    suspend fun save(entry: MemoryEntry)
    suspend fun get(id: String): MemoryEntry?
    suspend fun delete(id: String)
    suspend fun getAll(): List<MemoryEntry>
    suspend fun search(query: String): List<MemoryEntry>
    suspend fun clear()
}

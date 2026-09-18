package com.example.z.memory

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File

class JsonMemoryRepository(
    private val file: File
) : MemoryRepository {

    constructor(context: Context) : this(
        File(context.filesDir, "memory.json")
    )

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private suspend fun readEntries(): MutableList<MemoryEntry> =
        withContext(Dispatchers.IO) {
            if (!file.exists()) {
                mutableListOf()
            } else {
                runCatching {
                    json.decodeFromString<List<MemoryEntry>>(
                        file.readText()
                    ).toMutableList()
                }.getOrElse {
                    mutableListOf()
                }
            }
        }

    private suspend fun writeEntries(entries: List<MemoryEntry>) =
        withContext(Dispatchers.IO) {
            val tempFile = File(
                file.parentFile,
                "${file.name}.tmp"
            )

            tempFile.writeText(
                json.encodeToString(entries)
            )

            if (file.exists()) {
                file.delete()
            }

            tempFile.renameTo(file)
        }

    override suspend fun save(entry: MemoryEntry) {
        val entries = readEntries()
        val index = entries.indexOfFirst { it.id == entry.id }

        if (index >= 0) {
            entries[index] = entry
        } else {
            entries.add(entry)
        }

        writeEntries(entries)
    }

    override suspend fun get(id: String): MemoryEntry? =
        readEntries().firstOrNull { it.id == id }

    override suspend fun delete(id: String) {
        val entries = readEntries()
        entries.removeAll { it.id == id }
        writeEntries(entries)
    }

    override suspend fun getAll(): List<MemoryEntry> =
        readEntries()

    override suspend fun search(query: String): List<MemoryEntry> {
        val normalized = query.trim().lowercase()

        if (normalized.isEmpty()) {
            return emptyList()
        }

        return readEntries().filter {
            it.category.lowercase().contains(normalized) ||
            it.key.lowercase().contains(normalized) ||
            it.value.lowercase().contains(normalized)
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            if (file.exists()) {
                file.delete()
            }
        }
    }
}

package com.example.z.memory

import java.io.File
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class JsonMemoryRepositoryTest {

    private val testFiles = mutableListOf<File>()

    private fun repository(): JsonMemoryRepository {
        val file = File.createTempFile("z-memory-test-", ".json")
        file.delete()
        testFiles += file
        return JsonMemoryRepository(file)
    }

    private fun entry(
        id: String,
        key: String,
        value: String
    ) = MemoryEntry(
        id = id,
        category = "test",
        key = key,
        value = value,
        createdAt = 1L,
        updatedAt = 1L
    )

    @After
    fun tearDown() {
        testFiles.forEach { it.delete() }
        testFiles.clear()
    }

    @Test
    fun saveAndGet_returnsStoredEntry() = runBlocking {
        val repository = repository()
        val expected = entry("1", "name", "Z")

        repository.save(expected)

        assertEquals(expected, repository.get("1"))
    }

    @Test
    fun saveSameId_updatesExistingEntry() = runBlocking {
        val repository = repository()

        repository.save(entry("1", "name", "Z"))
        repository.save(entry("1", "name", "Z-AI"))

        assertEquals("Z-AI", repository.get("1")?.value)
        assertEquals(1, repository.getAll().size)
    }

    @Test
    fun delete_removesEntry() = runBlocking {
        val repository = repository()

        repository.save(entry("1", "name", "Z"))
        repository.delete("1")

        assertNull(repository.get("1"))
    }

    @Test
    fun search_findsCategoryKeyAndValue() = runBlocking {
        val repository = repository()

        repository.save(entry("1", "profile", "Umesh"))
        repository.save(entry("2", "settings", "Dark Mode"))

        assertTrue(repository.search("profile").any { it.id == "1" })
        assertTrue(repository.search("dark").any { it.id == "2" })
        assertTrue(repository.search("umesh").any { it.id == "1" })
    }

    @Test
    fun clear_removesAllEntries() = runBlocking {
        val repository = repository()

        repository.save(entry("1", "one", "first"))
        repository.save(entry("2", "two", "second"))

        repository.clear()

        assertTrue(repository.getAll().isEmpty())
    }

    @Test
    fun malformedJson_throwsInsteadOfReturningEmptyMemory() = runBlocking {
        val file = File.createTempFile("z-memory-malformed-", ".json")

        try {
            file.writeText("{not-valid-json")

            val repository = JsonMemoryRepository(file)

            var failed = false
            try {
                repository.getAll()
            } catch (_: Exception) {
                failed = true
            }

            assertTrue(failed)
        } finally {
            file.delete()
        }
    }
}

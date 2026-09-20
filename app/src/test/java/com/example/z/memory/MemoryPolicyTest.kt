package com.example.z.memory

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MemoryPolicyTest {

    private fun entry(
        category: String = "test",
        key: String = "key",
        value: String = "hello"
    ) = MemoryEntry(
        id = "test:1",
        category = category,
        key = key,
        value = value,
        createdAt = 1L,
        updatedAt = 1L
    )

    @Test
    fun normalEntry_isAllowed() {
        assertTrue(MemoryPolicy().canStore(entry()))
    }

    @Test
    fun blankValue_isRejected() {
        assertFalse(
            MemoryPolicy().canStore(
                entry(value = "   ")
            )
        )
    }

    @Test
    fun explicitSecretMarkers_areRejected() {
        val policy = MemoryPolicy()

        assertFalse(policy.canStore(entry(key = "password")))
        assertFalse(policy.canStore(entry(key = "api_key")))
        assertFalse(policy.canStore(entry(key = "access_token")))
        assertFalse(policy.canStore(entry(category = "secret")))
        assertFalse(policy.canStore(entry(category = "credentials")))
        assertFalse(policy.canStore(entry(key = "private_key")))
    }
}

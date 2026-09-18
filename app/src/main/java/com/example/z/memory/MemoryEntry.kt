package com.example.z.memory

import kotlinx.serialization.Serializable

@Serializable
data class MemoryEntry(
    val id: String,
    val category: String,
    val key: String,
    val value: String,
    val createdAt: Long,
    val updatedAt: Long,
    val importance: Int = 0
)

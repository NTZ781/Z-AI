package com.example.z.core

import com.example.z.ai.AIBrain
import com.example.z.ai.model.ModelManager
import com.example.z.memory.MemoryRepository

interface ZCore {
    val state: ZCoreState
    val aiBrain: AIBrain
    val modelManager: ModelManager

    val memory: MemoryRepository

    fun start()
    fun stop()
}

enum class ZCoreState {
    STOPPED,
    STARTING,
    READY,
    ERROR
}

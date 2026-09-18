package com.example.z.core

import android.app.Application
import com.example.z.ai.AIBrain
import com.example.z.ai.AIBrainImpl
import com.example.z.ai.AIProviderRegistry
import com.example.z.ai.AIProviderRouter
import com.example.z.ai.LocalAIProvider
import com.example.z.ai.config.AIProviderConfig
import com.example.z.ai.config.SecureApiKeyStore
import com.example.z.ai.online.OpenAICompatibleProvider
import com.example.z.ai.runtime.LlamaCppModelRuntime
import com.example.z.ai.runtime.UnavailableModelRuntime
import com.example.z.ai.model.DefaultModelManager
import com.example.z.ai.model.ModelManager
import com.example.z.ai.model.NoOpModelManager
import com.example.z.memory.JsonMemoryRepository
import com.example.z.memory.MemoryRepository
import com.example.z.memory.InMemoryMemoryRepository

class ZCoreImpl(
    private val application: Application? = null
) : ZCore {

    private val apiKeyStore = application?.let { SecureApiKeyStore(it) }
    private val localRuntime =
        application?.let {
            LlamaCppModelRuntime(it.applicationInfo.nativeLibraryDir)
        }

    private val providerRegistry = AIProviderRegistry()
    private val providerRouter = AIProviderRouter(providerRegistry)

    override val aiBrain: AIBrain = AIBrainImpl(providerRouter)

    override val modelManager: ModelManager =
        if (application != null && localRuntime != null) {
            DefaultModelManager(application, localRuntime)
        } else {
            NoOpModelManager()
        }

    override val memory: MemoryRepository = application?.let { JsonMemoryRepository(it) } ?: InMemoryMemoryRepository()

override var state: ZCoreState = ZCoreState.STOPPED
        private set

    init {
        providerRegistry.register(
            LocalAIProvider(localRuntime ?: UnavailableModelRuntime())
        )

        val onlineConfig = AIProviderConfig(
            endpoint = "",
            model = "",
            apiKey = null
        )

        providerRegistry.register(
            OpenAICompatibleProvider(
                endpoint = onlineConfig.endpoint,
                model = onlineConfig.model,
                apiKeyProvider = { apiKeyStore?.getApiKey() }
            )
        )
    }

    override fun start() {
        if (state == ZCoreState.READY) return
        state = ZCoreState.STARTING
        state = ZCoreState.READY
    }

    override fun stop() {
        state = ZCoreState.STOPPED
    }
}

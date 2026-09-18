package com.example.z.ai.online

import com.example.z.ai.AIProvider
import com.example.z.ai.AIProviderCapabilities
import com.example.z.ai.AIRequest
import com.example.z.ai.AIResponse
import com.example.z.ai.AITaskType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class OpenAICompatibleProvider(
    private val endpoint: String,
    private val model: String,
    private val apiKeyProvider: () -> String?
) : AIProvider {

    override val id: String = "online-openai-compatible"
    override val name: String = "Online AI"

    override val isAvailable: Boolean
        get() = endpoint.isNotBlank() &&
            model.isNotBlank() &&
            !apiKeyProvider().isNullOrBlank()

    override val capabilities = AIProviderCapabilities(
        setOf(
            AITaskType.GENERAL,
            AITaskType.REASONING,
            AITaskType.CODING,
            AITaskType.FAST_RESPONSE
        )
    )

    override suspend fun generate(request: AIRequest): AIResponse =
        withContext(Dispatchers.IO) {
            val key = apiKeyProvider()?.takeIf { it.isNotBlank() }
                ?: throw IllegalStateException("Online AI API key is not configured")

            val connection =
                (URL(endpoint).openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    connectTimeout = 15_000
                    readTimeout = 60_000
                    doOutput = true
                    setRequestProperty("Authorization", "Bearer $key")
                    setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Accept", "application/json")
                }

            try {
                val body = JSONObject()
                    .put("model", model)
                    .put(
                        "messages",
                        JSONArray().put(
                            JSONObject()
                                .put("role", "user")
                                .put("content", request.prompt)
                        )
                    )

                connection.outputStream.use { output ->
                    output.write(body.toString().toByteArray(Charsets.UTF_8))
                }

                val responseCode = connection.responseCode
                val responseText =
                    (if (responseCode in 200..299) {
                        connection.inputStream
                    } else {
                        connection.errorStream
                    })?.bufferedReader()?.use { it.readText() }

                if (responseCode !in 200..299) {
                    throw IllegalStateException(
                        "Online AI request failed: HTTP $responseCode"
                    )
                }

                val text = JSONObject(responseText ?: "")
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")

                AIResponse(
                    text = text,
                    providerId = id
                )
            } finally {
                connection.disconnect()
            }
        }
}

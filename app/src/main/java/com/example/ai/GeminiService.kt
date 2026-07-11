package com.example.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GeminiService {
    suspend fun generateContent(prompt: String): String = withContext(Dispatchers.IO) {
        // Mock response because API key usage is removed
        "AI Bot (Simulated): You said '$prompt'. Please configure an API key to enable real AI."
    }
}

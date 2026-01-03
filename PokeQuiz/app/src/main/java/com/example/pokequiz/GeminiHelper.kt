package com.example.pokequiz

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.ImagePart
import com.google.firebase.ai.type.ResponseModality
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object GeminiDeveloperApi25FlashModelConfiguration {
    // [START android_gemini_developer_api_gemini_25_flash_model]
    // Start by instantiating a GenerativeModel and specifying the model name:
    val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")
    // [END android_gemini_developer_api_gemini_25_flash_model]
}
fun textOnlyInput(scope: CoroutineScope) {
    val model = GeminiDeveloperApi25FlashModelConfiguration.model
    // [START android_gemini_developer_api_text_only_input]
    scope.launch {
        val response = model.generateContent("Write a story about a magic backpack.")
    }
    // [END android_gemini_developer_api_text_only_input]
}


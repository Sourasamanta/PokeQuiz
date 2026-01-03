package com.example.pokequiz

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Product(
    val question: String,
    val options: List<String>,
    val answer: String
)

@Serializable
data class QuizResponse(
    val questions: List<Product>
)

// For parsing single question (keep this for fallback)
// For parsing single question (keep this for fallback)
fun parseJSON(json: String): Product {
    val format = Json { ignoreUnknownKeys = true }
    val product = format.decodeFromString<Product>(json)

    println("Question: ${product.question}")
    product.options.forEachIndexed { index, option ->
        println("Option ${index + 1}: $option")
    }
    println("Answer: ${product.answer}")

    return product
}

// For parsing multiple questions (add this new function)
fun parseMultipleJSON(json: String): List<Product> {
    val format = Json { ignoreUnknownKeys = true }
    return try {
        val response = format.decodeFromString<QuizResponse>(json)
        println("Total questions: ${response.questions.size}")
        response.questions
    } catch (e: Exception) {
        println("Error parsing multiple questions: ${e.message}")
        emptyList()
    }
}
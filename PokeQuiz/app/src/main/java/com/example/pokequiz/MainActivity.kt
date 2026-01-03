package com.example.pokequiz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokequiz.ui.theme.PokeQuizTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.graphics.Color

val correctColor = Color(0xFFBAFAA5)
val wrongColor   = Color(0xFFFAADA5)
val defaultColor = Color(0xFFE0E0E0)




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeQuizTheme {
                var navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavController) {

    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            EnterNameScreen(navController)
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "trainer"

            // Pass playerName to your Quiz screen
            QuizScreen(
                navController = navController,
                playerName = playerName
            )
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("correct") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("playerName") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val correct = backStackEntry.arguments?.getInt("correct") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 1
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "trainer"

            ResultScreen(
                navController = navController,
                correctAnswers = correct,
                totalQuestions = total,
                playerName = playerName
            )
        }
    }
}




@Composable
fun GeminiScreen(
    modifier: Modifier = Modifier,
    scope: CoroutineScope
) {
    var prompt by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val model = GeminiDeveloperApi25FlashModelConfiguration.model

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "PokeQuiz - Gemini AI",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text("Enter your prompt") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            enabled = !isLoading
        )

        Button(
            onClick = {
                if (prompt.isNotBlank()) {
                    isLoading = true
                    scope.launch {
                        try {
                            val result = model.generateContent(prompt)
                            response = result.text ?: "No response received"
                        } catch (e: Exception) {
                            response = "Error: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                }
            },
            enabled = !isLoading && prompt.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Generating..." else "Generate")
        }

        if (isLoading) {
            CircularProgressIndicator()
        }

        if (response.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Response:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = response,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}



fun isInternetAvailable(context: Context): Boolean {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}



@Composable
fun GeminiHelper(
    modifier: Modifier = Modifier,
    refresh: Int,
    navController: NavHostController,
    playerName: String
){
    val prompt = """
Generate 20 Pokémon quiz questions covering all generations from Kanto to Galar in JSON format.

{
  "questions": [
    {
      "question": "Which Pokémon is known as the Electric Mouse Pokémon?",
      "options": ["Pikachu", "Raichu", "Pichu", "Dedenne"],
      "answer": "Pikachu"
    }
  ]
}

Rules:
- Pokémon-related only
- Options should be actual Pokémon names when possible
- Exactly one correct answer per question
- No explanation
- Output only valid JSON with "questions" array
- Generate exactly 20 questions
""".trimIndent()

    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var allQuestions by remember { mutableStateOf<List<Product>>(emptyList()) }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableStateOf(0) }  // Track correct answers
    var totalQuestions by remember { mutableStateOf(0) }  // Track total answered

    val model = GeminiDeveloperApi25FlashModelConfiguration.model

    // Function to load questions
    suspend fun loadQuestions() {
        isLoading = true
        try {
            val result = model.generateContent(prompt)
            val response = result.text ?: "No response received"
            Log.d("GeminiQuiz", "Raw Response: $response")

            val cleanedResponse = response
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()

            val parsed = parseMultipleJSON(cleanedResponse)
            Log.d("GeminiQuiz", "Parsed ${parsed.size} questions")

            if (parsed.isNotEmpty()) {
                allQuestions = parsed
                currentQuestionIndex = 0
            } else {
                errorMessage = "Failed to parse quiz questions"
            }
        } catch (e: Exception) {
            Log.e("GeminiQuiz", "Error: ${e.message}", e)
            errorMessage = "Error: ${e.message}"
            // Fallback to local questions
            val fallbackQuestions = mutableListOf<Product>()

            repeat(20) {
                val randomIndex = Random.nextInt(0, QuizList.quizList.size)
                val jsonString = QuizList.quizList[randomIndex]
                val question = parseJSON(jsonString)
                fallbackQuestions.add(question)
            }

            // Assign to allQuestions
            allQuestions = fallbackQuestions



        } finally {
            isLoading = false
        }
    }

    // Load initial batch of questions
    LaunchedEffect(Unit) {
        loadQuestions()
    }

    // Handle NEXT button clicks
    LaunchedEffect(refresh) {
        if (refresh > 0 && allQuestions.isNotEmpty() && !isLoading) {
            // Check if user answered before moving to next
            if (showResult && selectedOption != null) {
                // Count correct answer
                if (selectedOption == allQuestions[currentQuestionIndex].answer) {
                    correctAnswers++
                }
                totalQuestions++
            }

            // Reset for next question
            selectedOption = null
            showResult = false

            // Check if completed 20 questions
            if (currentQuestionIndex >= allQuestions.size - 1) {
                // Navigate to results page with score
                navController.navigate("result/$correctAnswers/$totalQuestions/$playerName")
            } else {
                // Move to next question
                currentQuestionIndex++
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
                Text(
                    "Generating quiz questions...",
                    fontFamily = FontFamily(Font(R.font.pokemon_solid))
                )
            }
            allQuestions.isNotEmpty() -> {
                val currentQuestion = allQuestions[currentQuestionIndex]
                val localContext = LocalContext.current

                // Progress indicator with score
                Text(
                    "Question ${currentQuestionIndex + 1} of ${allQuestions.size} | Score: $correctAnswers/$totalQuestions",
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily(Font(R.font.pokemon_solid))
                )

                var cardCol = MaterialTheme.colorScheme.surface

                Card(modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (!showResult) {
                            defaultColor
                        } else if (selectedOption == currentQuestion.answer) {
                            correctColor
                        } else {
                            wrongColor
                        }
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (showResult) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = if (selectedOption == currentQuestion.answer) {
                                    "✓ Correct!"
                                } else {
                                    "✗ Wrong! The correct answer is: ${currentQuestion.answer}"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                                color = if (selectedOption == currentQuestion.answer) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.error
                                }
                            )

                            if (currentQuestionIndex == allQuestions.size - 1) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Last question! Click NEXT to see results.",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                        Text(
                            text = currentQuestion.question,
                            style = MaterialTheme.typography.titleLarge,
                            fontFamily = FontFamily(Font(R.font.pokemon_solid))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        currentQuestion.options.forEach { option ->
                            OutlinedButton(
                                onClick = {
                                    selectedOption = option
                                    showResult = true
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                enabled = !showResult,
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = when {
                                        !showResult -> MaterialTheme.colorScheme.surface
                                        option == currentQuestion.answer -> MaterialTheme.colorScheme.primaryContainer
                                        option == selectedOption -> MaterialTheme.colorScheme.errorContainer
                                        else -> MaterialTheme.colorScheme.surface
                                    }
                                )
                            ) {
                                if(!isInternetAvailable(localContext)) {
                                    Text(
                                        option,
                                        fontFamily = FontFamily(Font(R.font.pokemon_solid))
                                    )
                                } else {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val pokemonId = PokemonDex.getOrDefault(option, 0)
                                        if (pokemonId != 0) {
                                            Image(
                                                painter = rememberAsyncImagePainter(
                                                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png"
                                                ),
                                                contentDescription = null,
                                                modifier = Modifier.size(100.dp)
                                            )
                                        }
                                        Text(
                                            option,
                                            fontFamily = FontFamily(Font(R.font.pokemon_solid))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


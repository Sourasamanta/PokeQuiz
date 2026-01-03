package com.example.pokequiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ResultScreen(
    navController: NavHostController,
    correctAnswers: Int,
    totalQuestions: Int,
    playerName: String
) {
    val percentage = (correctAnswers.toFloat() / totalQuestions * 100).toInt()

    Scaffold(
        topBar = { TopAppBar() } // Make sure you have a TopAppBar composable
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Quiz Complete!",
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                color = colorResource(id = R.color.red)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Your Score",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily(Font(R.font.pokemon_solid))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$correctAnswers / $totalQuestions",
                style = MaterialTheme.typography.displayLarge,
                fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                color = colorResource(id = R.color.blue)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                color = when {
                    percentage >= 80 -> colorResource(id = R.color.yellow)
                    percentage >= 60 -> colorResource(id = R.color.blue)
                    else -> colorResource(id = R.color.red)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = when {
                    percentage >= 90 -> "Amazing! Pokémon Master ${playerName}! "
                    percentage >= 70 -> "Great job ${playerName}! Keep training! "
                    percentage >= 50 -> "Good effort ${playerName}! You can do better! "
                    else -> "Keep practicing, ${playerName}!"
                },
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily(Font(R.font.pokemon_solid))
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.Quiz.route) {
                        popUpTo(Screen.Home.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.red)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "PLAY AGAIN",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                    color = colorResource(id = R.color.yellow)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "HOME",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                    color = colorResource(id = R.color.yellow)
                )
            }
        }
    }
}

package com.example.pokequiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun QuizScreen(navController: NavHostController, playerName: String) {
    var refresh by remember { mutableStateOf(0) }
    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(id = R.color.red),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clickable(
                        onClick = {refresh++}
                    )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "NEXT",
                        fontSize = 48.sp,
                        fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                        color = colorResource(id = R.color.yellow)
                    )
                }
            }
        }
    ){
            innerPadding ->
            GeminiHelper(modifier = Modifier.padding(innerPadding), refresh, navController, playerName = playerName )
    }
}

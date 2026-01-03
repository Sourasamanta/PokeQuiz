package com.example.pokequiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    TopAppBar(
        navigationIcon = {
            Image(painter = painterResource(id = R.drawable.pokeball), contentDescription = "PokeBall", modifier = Modifier.size(48.dp).padding(start = 16.dp))},
        title = {
            Text(
                text = "PokeQuiz",
                letterSpacing = 2.5.sp,
                fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                color = colorResource(id = R.color.yellow),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.blue),
        )
    )
}

@Composable
fun BottomBar(showDialog: MutableState<Boolean>) {
    BottomAppBar(
        containerColor = colorResource(id = R.color.red),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable {
                showDialog.value = true
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "START",
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.pokemon_solid)),
                color = colorResource(id = R.color.yellow)
            )
        }
    }
}


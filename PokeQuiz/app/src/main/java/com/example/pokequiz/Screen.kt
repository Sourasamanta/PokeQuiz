package com.example.pokequiz

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Quiz : Screen("quiz/{playerName}") {
        fun createRoute(playerName: String) = "quiz/$playerName"
    }
    object Result : Screen("result/{correct}/{total}/{playerName}") {
        fun createRoute(correct: Int, total: Int, playerName: String) =
            "result/$correct/$total/$playerName"
    }
}

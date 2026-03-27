package com.sup2vinci.cocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.sup2vinci.cocktailapp.viewmodel.MainViewModel
import com.sup2vinci.cocktailapp.view.MainScreen
import com.sup2vinci.cocktailapp.view.SecondScreen
import com.sup2vinci.cocktailapp.view.DetailScreen
import com.sup2vinci.cocktailapp.ui.theme.CocktailAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CocktailAppTheme {
                var currentScreen by remember { mutableStateOf("main") }

                when (currentScreen) {

                    "main" -> {
                        MainScreen(
                            viewModel = viewModel,
                            onNavigate = { currentScreen = "second" }
                        )
                    }

                    "second" -> {
                        SecondScreen(
                            viewModel = viewModel,
                            onBack = { currentScreen = "main" },
                            onCocktailClick = { currentScreen = "detail" }
                        )
                    }

                    "detail" -> {
                        DetailScreen(
                            viewModel = viewModel,
                            onBack = { currentScreen = "second" }
                        )
                    }
                }
            }
        }
    }
}
package com.sup2vinci.cocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sup2vinci.cocktailapp.viewmodel.MainViewModel
import com.sup2vinci.cocktailapp.view.MainScreen
import com.sup2vinci.cocktailapp.view.SecondScreen
import com.sup2vinci.cocktailapp.view.DetailScreen
import com.sup2vinci.cocktailapp.view.FavoritesScreen
import com.sup2vinci.cocktailapp.view.SplashScreen
import com.sup2vinci.cocktailapp.ui.theme.CocktailAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            CocktailAppTheme {
                // État pour savoir si on affiche encore le splash screen personnalisé
                var showSplashScreen by remember { mutableStateOf(true) }

                if (showSplashScreen) {
                    SplashScreen(onFinished = { showSplashScreen = false })
                } else {
                    var currentTab by remember { mutableStateOf("home") }
                    var showDetail by remember { mutableStateOf(false) }

                    Scaffold(
                        bottomBar = {
                            if (!showDetail) {
                                NavigationBar {
                                    NavigationBarItem(
                                        selected = currentTab == "home",
                                        onClick = { currentTab = "home" },
                                        icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                                        label = { Text("Accueil") }
                                    )
                                    NavigationBarItem(
                                        selected = currentTab == "search",
                                        onClick = { currentTab = "search" },
                                        icon = { Icon(Icons.Default.Search, contentDescription = "Recherche") },
                                        label = { Text("Recherche") }
                                    )
                                    NavigationBarItem(
                                        selected = currentTab == "favorites",
                                        onClick = { currentTab = "favorites" },
                                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoris") },
                                        label = { Text("Favoris") }
                                    )
                                }
                            }
                        }
                    ) { paddingValues ->
                        Surface(modifier = Modifier.padding(paddingValues)) {
                            if (showDetail) {
                                DetailScreen(
                                    viewModel = viewModel,
                                    onBack = { showDetail = false }
                                )
                            } else {
                                when (currentTab) {
                                    "home" -> MainScreen(viewModel = viewModel)
                                    "search" -> SecondScreen(
                                        viewModel = viewModel,
                                        onCocktailClick = { showDetail = true }
                                    )
                                    "favorites" -> FavoritesScreen(
                                        viewModel = viewModel,
                                        onCocktailClick = { showDetail = true }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

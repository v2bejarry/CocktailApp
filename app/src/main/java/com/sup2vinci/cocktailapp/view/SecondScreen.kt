package com.sup2vinci.cocktailapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sup2vinci.cocktailapp.viewmodel.MainViewModel
import com.sup2vinci.cocktailapp.viewmodel.CocktailState

@Composable

fun SecondScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onCocktailClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.searchCocktailState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    val favorites by viewModel.favorites.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {


        Button(onClick = {
            viewModel.resetSearchState()
            onBack()
        }) {
            Text("Retour")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Rechercher un cocktail",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔍 Champ de recherche
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Nom du cocktail") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔎 Bouton recherche
        Button(
            onClick = {
                if (query.isBlank()) {
                    errorMessage = "Veuillez entrer du texte"
                } else {
                    errorMessage = ""
                    viewModel.searchCocktails(query)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Rechercher")
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        when (state) {

            is CocktailState.Idle -> {

            }

            is CocktailState.Loading -> {
                CircularProgressIndicator()
            }

            is CocktailState.Success -> {
                val cocktails = (state as CocktailState.Success).cocktails
                LazyColumn {
                    items(cocktails) { drink ->

                        val isFav = favorites.any { it.id == drink.id }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            onClick = {
                                viewModel.selectCocktail(drink)
                                onCocktailClick()
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column {
                                    Text(
                                        text = drink.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = drink.category ?: "Catégorie inconnue",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                IconButton(onClick = {
                                    viewModel.toggleFavorite(drink)
                                }) {
                                    Text(if (isFav) "❤️" else "🤍")
                                }
                            }
                        }
                    }
                }
            }

            is CocktailState.Error -> {
                Text(
                    text = (state as CocktailState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
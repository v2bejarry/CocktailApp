package com.sup2vinci.cocktailapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sup2vinci.cocktailapp.model.Drink
import com.sup2vinci.cocktailapp.viewmodel.CocktailState
import com.sup2vinci.cocktailapp.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel, onNavigate: () -> Unit) {
    val state by viewModel.randomCocktailState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { onNavigate() }) {
            Text(text = "Aller au deuxième écran", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is CocktailState.Loading -> {
                Text("Chargement du cocktail...", style = MaterialTheme.typography.bodyLarge)
            }

            is CocktailState.Error -> {
                Text(
                    "Erreur : ${(state as CocktailState.Error).message}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            is CocktailState.Success -> {
                val cocktail = (state as CocktailState.Success).cocktails.first()
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(onClick = { viewModel.fetchRandomCocktail() }) {
                        Text("Un autre cocktail", style = MaterialTheme.typography.bodyLarge)
                    }
                    Text("Nom : ${cocktail.name}", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "Catégorie : ${cocktail.category ?: "Inconnue"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Instructions : ${cocktail.instructions ?: "Aucune"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    // si tu veux l'image :
                    // AsyncImage(model = cocktail.thumbnail, contentDescription = cocktail.name)
                }
            }
            is CocktailState.Idle -> { /* rien à afficher */ }
        }
    }
}
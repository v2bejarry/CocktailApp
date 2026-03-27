package com.sup2vinci.cocktailapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sup2vinci.cocktailapp.viewmodel.MainViewModel

@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val cocktail by viewModel.selectedCocktail.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    val isFav = cocktail?.let { drink ->
        favorites.any { it.id == drink.id }
    } ?: false

    Column(modifier = Modifier.padding(16.dp)) {

        Button(onClick = { onBack() }) {
            Text("Retour")
        }

        Spacer(modifier = Modifier.height(16.dp))

        cocktail?.let { drink ->
            val isFav = cocktail?.let { viewModel.isFavorite(it) } ?: false

            Text(drink.name, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Text("Catégorie : ${drink.category ?: "Inconnue"}")

            Spacer(modifier = Modifier.height(8.dp))

            Text("Instructions :")
            Text(drink.instructions ?: "Aucune instruction")


        Button(
            onClick = {
                cocktail?.let { viewModel.toggleFavorite(it) }
            }
        ) {
            Text(if (isFav) "❤️ Retirer des favoris" else "🤍 Ajouter aux favoris")
        }
        }
    }
}

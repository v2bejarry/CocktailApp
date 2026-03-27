package com.sup2vinci.cocktailapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sup2vinci.cocktailapp.viewmodel.MainViewModel

@Composable
fun FavoritesScreen(
    viewModel: MainViewModel,
    onCocktailClick: () -> Unit
) {
    val favorites by viewModel.favorites.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Mes Favoris",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Aucun favori pour le moment", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites) { drink ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.selectCocktail(drink)
                            onCocktailClick()
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = drink.thumbnail,
                                contentDescription = drink.name,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(drink.name, style = MaterialTheme.typography.titleMedium)
                                Text(drink.category ?: "", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { viewModel.toggleFavorite(drink) }) {
                                Text("❤️")
                            }
                        }
                    }
                }
            }
        }
    }
}

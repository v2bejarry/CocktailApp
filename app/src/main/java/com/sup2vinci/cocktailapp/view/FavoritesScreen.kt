package com.sup2vinci.cocktailapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                            modifier = Modifier.fillMaxWidth().height(80.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = drink.thumbnail,
                                contentDescription = drink.name,
                                modifier = Modifier.size(80.dp),
                                contentScale = ContentScale.Crop
                            )
                            
                            Column(
                                modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
                            ) {
                                Text(
                                    text = drink.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1
                                )
                                Text(
                                    text = drink.category ?: "Inconnu",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            IconButton(onClick = { viewModel.toggleFavorite(drink) }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Retirer des favoris",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

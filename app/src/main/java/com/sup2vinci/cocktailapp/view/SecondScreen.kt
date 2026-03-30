package com.sup2vinci.cocktailapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sup2vinci.cocktailapp.viewmodel.MainViewModel
import com.sup2vinci.cocktailapp.viewmodel.CocktailState

@Composable
fun SecondScreen(
    viewModel: MainViewModel,
    onCocktailClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.searchCocktailState.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Recherche"
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { 
                query = it
                if (it.length > 2) viewModel.searchCocktails(it)
            },
            label = { Text("Nom du cocktail") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { viewModel.searchCocktails(query) }) {
                    Icon(Icons.Default.Search, contentDescription = "Rechercher")
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is CocktailState.Idle -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Commencez à taper pour rechercher un cocktail", style = MaterialTheme.typography.bodyMedium)
                }
            }
            is CocktailState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is CocktailState.Success -> {
                val cocktails = (state as CocktailState.Success).cocktails
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(cocktails) { drink ->
                        val isFav = favorites.any { it.id == drink.id }

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
                                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favori",
                                        tint = if (isFav) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is CocktailState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = (state as CocktailState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

package com.sup2vinci.cocktailapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sup2vinci.cocktailapp.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cocktail?.name ?: "Détails") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    cocktail?.let { drink ->
                        IconButton(onClick = { viewModel.toggleFavorite(drink) }) {
                            Icon(
                                if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favori",
                                tint = if (isFav) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        cocktail?.let { drink ->
            val ingredients = drink.getIngredientsWithMeasures()
            val difficultyCount = when {
                ingredients.size <= 4 -> 1
                ingredients.size <= 7 -> 2
                else -> 3
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = drink.thumbnail,
                    contentDescription = drink.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = drink.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Text(
                                text = drink.category ?: "Catégorie inconnue",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        // Badge de difficulté
                        Surface(
                            color = when(difficultyCount) {
                                1 -> Color(0xFF4CAF50) // Vert
                                2 -> Color(0xFFFF9800) // Orange
                                else -> Color(0xFFF44336) // Rouge
                            }.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(3) { index ->
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = if (index < difficultyCount) {
                                            when(difficultyCount) {
                                                1 -> Color(0xFF4CAF50)
                                                2 -> Color(0xFFFF9800)
                                                else -> Color(0xFFF44336)
                                            }
                                        } else Color.Gray.copy(alpha = 0.3f)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Niv. $difficultyCount",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = when(difficultyCount) {
                                        1 -> Color(0xFF2E7D32)
                                        2 -> Color(0xFFEF6C00)
                                        else -> Color(0xFFC62828)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section Ingrédients
                    Text(
                        text = "Ingrédients (${ingredients.size})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    ingredients.forEach { (ingredient, measure) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = if (measure.isNotBlank()) "$ingredient ($measure)" else ingredient,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section Instructions
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = drink.instructions ?: "Aucune instruction disponible",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

package com.sup2vinci.cocktailapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sup2vinci.cocktailapp.model.Drink
import com.sup2vinci.cocktailapp.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _randomCocktailState = MutableStateFlow<CocktailState>(CocktailState.Loading)
    val randomCocktailState: StateFlow<CocktailState> = _randomCocktailState

    private val _searchCocktailState = MutableStateFlow<CocktailState>(CocktailState.Idle)
    val searchCocktailState: StateFlow<CocktailState> = _searchCocktailState

    private val _selectedCocktail = MutableStateFlow<Drink?>(null)
    val selectedCocktail: StateFlow<Drink?> = _selectedCocktail
    private val _favorites = MutableStateFlow<List<Drink>>(emptyList())
    val favorites: StateFlow<List<Drink>> = _favorites

    init {
        fetchRandomCocktail()
    }

    fun fetchRandomCocktail() {
        viewModelScope.launch {
            _randomCocktailState.value = CocktailState.Loading
            try {
                val response = RetrofitInstance.api.getCocktail()
                if (response.drinks.isNotEmpty()) {
                    _randomCocktailState.value = CocktailState.Success(response.drinks)
                } else {
                    _randomCocktailState.value = CocktailState.Error("Aucun cocktail trouvé")
                }
            } catch (e: Exception) {
                _randomCocktailState.value = CocktailState.Error("Erreur réseau : ${e.message}")
            }
        }
    }

    fun searchCocktails(name: String) {
        viewModelScope.launch {
            _searchCocktailState.value = CocktailState.Loading
            try {
                val response = RetrofitInstance.api.searchCocktail(name)
                if (!response.drinks.isNullOrEmpty()) {
                    _searchCocktailState.value = CocktailState.Success(response.drinks)
                } else {
                    _searchCocktailState.value = CocktailState.Error("Aucun résultat")
                }
            } catch (e: Exception) {
                _searchCocktailState.value = CocktailState.Error("Erreur : ${e.message}")
            }
        }
    }

    fun resetSearchState() {
        _searchCocktailState.value = CocktailState.Idle
    }

    fun selectCocktail(drink: Drink) {
        _selectedCocktail.value = drink
    }

    fun toggleFavorite(drink: Drink) {
        val current = _favorites.value.toMutableList()

        if (current.any { it.id == drink.id }) {
            current.removeAll { it.id == drink.id }
        } else {
            current.add(drink)
        }

        _favorites.value = current
    }

    fun isFavorite(drink: Drink): Boolean {
        return _favorites.value.any { it.id == drink.id }
    }
}
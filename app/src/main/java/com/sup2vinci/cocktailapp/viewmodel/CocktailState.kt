package com.sup2vinci.cocktailapp.viewmodel

import com.sup2vinci.cocktailapp.model.Drink

sealed interface CocktailState {
    object Loading : CocktailState
    object Idle : CocktailState
    data class Success(val cocktails: List<Drink>) : CocktailState
    data class Error(val message: String) : CocktailState
}
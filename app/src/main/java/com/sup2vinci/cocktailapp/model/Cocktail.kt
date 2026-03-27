package com.sup2vinci.cocktailapp.model

import com.google.gson.annotations.SerializedName

// Pour mapper la réponse JSON de l'API CocktailDB
data class CocktailResponse(
    @SerializedName("drinks") val drinks: List<Drink>
)

data class Drink(
    @SerializedName("idDrink") val id: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strDrinkThumb") val thumbnail: String?
)
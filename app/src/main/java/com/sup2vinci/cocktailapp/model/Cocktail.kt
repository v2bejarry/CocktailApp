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
    @SerializedName("strDrinkThumb") val thumbnail: String?,
    @SerializedName("strIngredient1") val ingredient1: String? = null,
    @SerializedName("strIngredient2") val ingredient2: String? = null,
    @SerializedName("strIngredient3") val ingredient3: String? = null,
    @SerializedName("strIngredient4") val ingredient4: String? = null,
    @SerializedName("strIngredient5") val ingredient5: String? = null,
    @SerializedName("strIngredient6") val ingredient6: String? = null,
    @SerializedName("strIngredient7") val ingredient7: String? = null,
    @SerializedName("strIngredient8") val ingredient8: String? = null,
    @SerializedName("strIngredient9") val ingredient9: String? = null,
    @SerializedName("strIngredient10") val ingredient10: String? = null,
    @SerializedName("strIngredient11") val ingredient11: String? = null,
    @SerializedName("strIngredient12") val ingredient12: String? = null,
    @SerializedName("strIngredient13") val ingredient13: String? = null,
    @SerializedName("strIngredient14") val ingredient14: String? = null,
    @SerializedName("strIngredient15") val ingredient15: String? = null,
    @SerializedName("strMeasure1") val measure1: String? = null,
    @SerializedName("strMeasure2") val measure2: String? = null,
    @SerializedName("strMeasure3") val measure3: String? = null,
    @SerializedName("strMeasure4") val measure4: String? = null,
    @SerializedName("strMeasure5") val measure5: String? = null,
    @SerializedName("strMeasure6") val measure6: String? = null,
    @SerializedName("strMeasure7") val measure7: String? = null,
    @SerializedName("strMeasure8") val measure8: String? = null,
    @SerializedName("strMeasure9") val measure9: String? = null,
    @SerializedName("strMeasure10") val measure10: String? = null,
    @SerializedName("strMeasure11") val measure11: String? = null,
    @SerializedName("strMeasure12") val measure12: String? = null,
    @SerializedName("strMeasure13") val measure13: String? = null,
    @SerializedName("strMeasure14") val measure14: String? = null,
    @SerializedName("strMeasure15") val measure15: String? = null
) {
    fun getIngredientsWithMeasures(): List<Pair<String, String>> {
        val list = mutableListOf<Pair<String, String>>()
        if (!ingredient1.isNullOrBlank()) list.add(ingredient1 to (measure1 ?: ""))
        if (!ingredient2.isNullOrBlank()) list.add(ingredient2 to (measure2 ?: ""))
        if (!ingredient3.isNullOrBlank()) list.add(ingredient3 to (measure3 ?: ""))
        if (!ingredient4.isNullOrBlank()) list.add(ingredient4 to (measure4 ?: ""))
        if (!ingredient5.isNullOrBlank()) list.add(ingredient5 to (measure5 ?: ""))
        if (!ingredient6.isNullOrBlank()) list.add(ingredient6 to (measure6 ?: ""))
        if (!ingredient7.isNullOrBlank()) list.add(ingredient7 to (measure7 ?: ""))
        if (!ingredient8.isNullOrBlank()) list.add(ingredient8 to (measure8 ?: ""))
        if (!ingredient9.isNullOrBlank()) list.add(ingredient9 to (measure9 ?: ""))
        if (!ingredient10.isNullOrBlank()) list.add(ingredient10 to (measure10 ?: ""))
        if (!ingredient11.isNullOrBlank()) list.add(ingredient11 to (measure11 ?: ""))
        if (!ingredient12.isNullOrBlank()) list.add(ingredient12 to (measure12 ?: ""))
        if (!ingredient13.isNullOrBlank()) list.add(ingredient13 to (measure13 ?: ""))
        if (!ingredient14.isNullOrBlank()) list.add(ingredient14 to (measure14 ?: ""))
        if (!ingredient15.isNullOrBlank()) list.add(ingredient15 to (measure15 ?: ""))
        return list
    }
}

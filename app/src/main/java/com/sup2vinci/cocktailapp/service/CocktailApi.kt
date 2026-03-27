package com.sup2vinci.cocktailapp.service

import com.sup2vinci.cocktailapp.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("random.php")
    suspend fun getCocktail(): CocktailResponse

    @GET("search.php")
    suspend fun searchCocktail(
        @Query("s") name: String
    ): CocktailResponse
}


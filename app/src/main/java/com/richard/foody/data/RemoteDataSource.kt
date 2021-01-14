package com.richard.foody.data

import com.richard.foody.data.netwotk.FoodRecipesApi
import com.richard.foody.models.FoodJoke
import com.richard.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource
@Inject
constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return foodRecipesApi.getFoodJoke(apiKey)
    }
}
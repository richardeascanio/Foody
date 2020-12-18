package com.richard.foody.data

import com.richard.foody.data.netwotk.FoodRecipesApi
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

}
package com.richard.foody.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.richard.foody.models.FoodRecipe
import com.richard.foody.utils.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
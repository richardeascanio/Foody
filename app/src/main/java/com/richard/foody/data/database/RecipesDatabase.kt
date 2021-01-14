package com.richard.foody.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.richard.foody.data.database.entities.FavouritesEntity
import com.richard.foody.data.database.entities.FoodJokeEntity
import com.richard.foody.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavouritesEntity::class, FoodJokeEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}
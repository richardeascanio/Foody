package com.richard.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.richard.foody.models.Result
import com.richard.foody.utils.Constants.FAVOURITE_RECIPES_TABLE

@Entity(tableName = FAVOURITE_RECIPES_TABLE)
class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)
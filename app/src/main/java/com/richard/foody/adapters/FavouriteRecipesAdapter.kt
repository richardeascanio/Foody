package com.richard.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.richard.foody.data.database.entities.FavouritesEntity
import com.richard.foody.databinding.FavouriteRecipesRowLayoutBinding
import com.richard.foody.utils.RecipesDiffUtil

class FavouriteRecipesAdapter: RecyclerView.Adapter<FavouriteRecipesAdapter.FavouriteRecipesViewHolder>() {

    private var favouritesEntitiesList = emptyList<FavouritesEntity>()

    class FavouriteRecipesViewHolder(private val binding: FavouriteRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(favouritesEntity: FavouritesEntity) {
            binding.favouritesEntity = favouritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavouriteRecipesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavouriteRecipesRowLayoutBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return FavouriteRecipesViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipesViewHolder {
        return FavouriteRecipesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavouriteRecipesViewHolder, position: Int) {
        val selectedRecipe = favouritesEntitiesList[position]
        holder.bind(selectedRecipe)
    }

    override fun getItemCount(): Int {
        return favouritesEntitiesList.size
    }

    fun setData(newFavouriteRecipes: List<FavouritesEntity>) {
        val favouriteRecipesDiffUtil = RecipesDiffUtil(favouritesEntitiesList, newFavouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favouriteRecipesDiffUtil)
        favouritesEntitiesList = newFavouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

}
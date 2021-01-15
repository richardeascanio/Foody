package com.richard.foody.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.richard.foody.R
import com.richard.foody.data.database.entities.FavouritesEntity
import com.richard.foody.databinding.FavouriteRecipesRowLayoutBinding
import com.richard.foody.ui.fragments.favourites.FavouriteRecipesFragmentDirections
import com.richard.foody.utils.RecipesDiffUtil
import com.richard.foody.viewmodels.MainViewModel

class FavouriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
): RecyclerView.Adapter<FavouriteRecipesAdapter.FavouriteRecipesViewHolder>(), ActionMode.Callback {

    private var favouritesEntitiesList = emptyList<FavouritesEntity>()
    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavouritesEntity>()
    private var myViewHolders = arrayListOf<FavouriteRecipesViewHolder>()
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    class FavouriteRecipesViewHolder(val binding: FavouriteRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

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
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentRecipe = favouritesEntitiesList[position]
        holder.bind(currentRecipe)

        // Single click listener
        holder.binding.favouriteRecipeRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action = FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }
        }

        // Long click listener
        holder.binding.favouriteRecipeRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    private fun applySelection(holder: FavouriteRecipesViewHolder, currentRecipe: FavouritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: FavouriteRecipesViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.favouriteRecipeRowLayout.setBackgroundColor(ContextCompat.getColor(requireActivity, backgroundColor))
        holder.binding.favouriteRowCardView.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    override fun getItemCount(): Int {
        return favouritesEntitiesList.size
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favourites_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
        // Delete items selected
        if (menu?.itemId == R.id.delete_favourite_recipe_menu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavouriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed")

            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavouriteRecipes: List<FavouritesEntity>) {
        val favouriteRecipesDiffUtil = RecipesDiffUtil(favouritesEntitiesList, newFavouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favouriteRecipesDiffUtil)
        favouritesEntitiesList = newFavouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        )
            .setAction("Ok") {}
            .show()
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

}
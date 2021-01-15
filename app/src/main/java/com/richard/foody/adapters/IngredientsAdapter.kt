package com.richard.foody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.richard.foody.R
import com.richard.foody.databinding.IngredientsRowLayoutBinding
import com.richard.foody.models.ExtendedIngredient
import com.richard.foody.utils.Constants.BASE_IMAGE_URL
import com.richard.foody.utils.RecipesDiffUtil
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class IngredientsViewHolder(val binding: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(IngredientsRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.binding.apply {
            ingredientImageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            ingredientName.text = ingredientsList[position].name.capitalize(Locale.ROOT)
            ingredientAmount.text = ingredientsList[position].amount.toString()
            ingredientUnit.text = ingredientsList[position].unit
            ingredientConsistency.text = ingredientsList[position].consistency
            ingredientOriginal.text = ingredientsList[position].original
        }
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
package com.richard.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.richard.foody.R
import com.richard.foody.adapters.PagerAdapter
import com.richard.foody.data.database.entities.FavouritesEntity
import com.richard.foody.databinding.ActivityDetailsBinding
import com.richard.foody.ui.fragments.ingredients.IngredientsFragment
import com.richard.foody.ui.fragments.instructions.InstructionsFragment
import com.richard.foody.ui.fragments.overview.OverviewFragment
import com.richard.foody.utils.Constants.RECIPE_RESULT_KEY
import com.richard.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favourites_menu)
        changeMenuItemColor(menuItem!!, R.color.white)
        checkSavedRecipes(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.save_to_favourites_menu -> {
                if (recipeSaved)
                    removeFromFavourites(item)
                else
                    saveToFavourites(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavouriteRecipes.observe(this) { favouriteEntityList ->
            try {
                for (savedRecipe in favouriteEntityList) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        recipeSaved = true
                        savedRecipeId = savedRecipe.id
                    }
                }
            } catch (e: Exception) {
                Log.d("Details Activity", "checkSavedRecipes: ${e.message.toString()}")
            }
        }
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouritesEntity = FavouritesEntity(
            0,
            args.result
        )
        mainViewModel.insertFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved.")
        recipeSaved = true
    }

    private fun removeFromFavourites(menuItem: MenuItem) {
        val favouritesEntity = FavouritesEntity(
            savedRecipeId,
            args.result
        )
        mainViewModel.deleteFavouriteRecipe(favouritesEntity)
        changeMenuItemColor(menuItem, R.color.white)
        showSnackBar("Removed from favourites.")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
            .setAction("Ok"){}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

}
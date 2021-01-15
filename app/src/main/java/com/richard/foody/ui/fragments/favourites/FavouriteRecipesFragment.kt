package com.richard.foody.ui.fragments.favourites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.richard.foody.R
import com.richard.foody.adapters.FavouriteRecipesAdapter
import com.richard.foody.databinding.FragmentFavouriteRecipesBinding
import com.richard.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavouriteRecipesAdapter by lazy { FavouriteRecipesAdapter(requireActivity(), mainViewModel) }

    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteRecipesBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter

        setHasOptionsMenu(true)
        setUpRecyclerView(binding.favouriteRecipesRecyclerView)

        return binding.root
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.root,
            "All recipes removed",
            Snackbar.LENGTH_SHORT
        )
            .setAction("Ok") {}
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(
            R.menu.favourites_recipes_menu,
            menu
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favourite_recipes_menu) {
            mainViewModel.deleteAllFavouriteRecipes()
            showSnackBar()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mAdapter.clearContextualActionMode()
    }

}
package com.richard.foody.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.richard.foody.R
import com.richard.foody.adapters.IngredientsAdapter
import com.richard.foody.databinding.FragmentIngredientsBinding
import com.richard.foody.models.Result
import com.richard.foody.utils.Constants.RECIPE_RESULT_KEY

class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setUpRecyclerView()
        myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.ingredientsRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
}
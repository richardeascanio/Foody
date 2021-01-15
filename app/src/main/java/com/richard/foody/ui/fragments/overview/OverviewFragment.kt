package com.richard.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.richard.foody.R
import com.richard.foody.databinding.FragmentOverviewBinding
import com.richard.foody.models.Result
import com.richard.foody.utils.Constants.RECIPE_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        myBundle?.let {
            binding.mainImageView.load(
                it.image
            )
            binding.titleTextView.text = it.title
            binding.likesTextView.text = it.aggregateLikes.toString()
            binding.timeTextView.text = it.readyInMinutes.toString()
            val summary = Jsoup.parse(myBundle.summary).text()
            binding.summaryTextView.text = summary

            if (myBundle.vegetarian) {
                binding.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.vegan) {
                binding.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.glutenFree) {
                binding.glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.dairyFree) {
                binding.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.veryHealthy) {
                binding.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.cheap) {
                binding.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
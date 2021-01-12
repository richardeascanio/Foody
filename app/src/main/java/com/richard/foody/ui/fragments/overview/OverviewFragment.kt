package com.richard.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.richard.foody.R
import com.richard.foody.models.Result
import com.richard.foody.utils.Constants.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_overview.view.*
import kotlinx.android.synthetic.main.fragment_overview.view.title_textView
import kotlinx.android.synthetic.main.recipes_row_layout.view.*
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        myBundle?.let {
            mView.mainImageView.load(
                myBundle.image
            )
            mView.title_textView.text = myBundle.title
            mView.likes_textView.text = myBundle.aggregateLikes.toString()
            mView.time_textView.text = myBundle.readyInMinutes.toString()
            val summary = Jsoup.parse(myBundle.summary).text()
            mView.summary_textView.text = summary

            if (myBundle.vegetarian) {
                mView.vegetarian_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                mView.vegetarian_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.vegan) {
                mView.vegan_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                mView.vegan_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.glutenFree) {
                mView.glutenFree_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                mView.glutenFree_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.dairyFree) {
                mView.dairyFree_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                mView.dairyFree_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.veryHealthy) {
                mView.healthy_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                mView.healthy_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            if (myBundle.cheap) {
                mView.cheap_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                mView.cheap_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
        }

        return mView
    }

}
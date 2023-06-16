package com.example.nychighschool.ui.satInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nychighschool.databinding.FragmentSatBinding
import com.example.nychighschool.models.HighSchool
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatInfoFragment : Fragment() {

    private var binding : FragmentSatBinding? = null

    private val viewModel : SatInfoViewModel by viewModels()

    private var highSchool : HighSchool? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSatBinding.inflate(inflater, container, false)
        return binding?.root
    }

    //TODO: if given more time, add refresh button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.satResult.observe(viewLifecycleOwner) {
            // if response is successful, continue to show view with high school SAT and other details. Else toast user to try again
            if (it.isSuccessful) {

                //TODO: if given more time,account for fields that are empty such as email and adjust views accordingly
                    binding?.satRetryButton?.visibility = View.INVISIBLE
                    binding?.satSchoolNameTextView?.text = highSchool?.schoolName
                    val address = highSchool?.schoolAddress + ", " + highSchool?.city + ", " + highSchool?.State + ", " + highSchool?.zip
                    binding?.satAddressTextView?.text = address
                    binding?.satPhoneTextView?.text = highSchool?.phone
                    binding?.satWebsiteTextView?.text = highSchool?.website
                    binding?.satOverviewTextView?.text = highSchool?.overview
                    binding?.satEmailTextView?.text = highSchool?.email
                    binding?.satMathScoreTextView?.text = it.body()?.getOrNull(0)?.math ?: " n/a"
                    binding?.satReadingScoreTextView?.text = it.body()?.getOrNull(0)?.reading ?: " n/a"
                    binding?.satWritingScoreTextView?.text = it.body()?.getOrNull(0)?.writing ?: " n/a"
            }
            else {
                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                binding?.satRetryButton?.visibility = View.VISIBLE
            }
        }

        // on click listener for refresh button to fetch SAT from api. It uses the high school object passed from high school fragment
        binding?.satRetryButton?.setOnClickListener {
            arguments?.let { arg ->
                highSchool = SatInfoFragmentArgs.fromBundle(arg).highSchool
                highSchool?.dbn?.let { hs -> viewModel.getSatInfo(hs) }
            }
        }

        //uses the high school object passed from high school fragment. It fetches SAT api with dbn argument
        arguments?.let { arg ->
            highSchool = SatInfoFragmentArgs.fromBundle(arg).highSchool
            highSchool?.dbn?.let { hs -> viewModel.getSatInfo(hs) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
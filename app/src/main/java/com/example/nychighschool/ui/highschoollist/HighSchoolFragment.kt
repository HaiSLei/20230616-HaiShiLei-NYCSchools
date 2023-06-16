package com.example.nychighschool.ui.highschoollist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nychighschool.databinding.FragmentHighSchoolBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HighSchoolFragment : Fragment() {

    private val viewModel : HighSchoolViewModel by viewModels()


    // initialize adapter by lazy and pass lambda body of navigation logic to adapter
    private val highSchoolAdapter by lazy {
        HighSchoolAdapter({
            val direction = HighSchoolFragmentDirections.actionHighSchoolToSat(it)
            findNavController().navigate(direction)
        })
    }

    private var binding : FragmentHighSchoolBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHighSchoolBinding.inflate(inflater, container, false)
        return binding?.root
    }

    //TODO: if given more time, add refresh button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.highSchoolRecyclerView?.adapter = highSchoolAdapter

        // observe the school list from view model and submit the list to adapter if successful
        viewModel.schoolListResult.observe(viewLifecycleOwner) {
            // if successful, submit the list to adapter else show refresh button
            if (it.isSuccessful) {
                binding?.highSchoolRefreshButton?.visibility = View.INVISIBLE
                highSchoolAdapter.submitList(it.body())
            }
            else {
                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                binding?.highSchoolRefreshButton?.visibility = View.VISIBLE
            }
        }

        // make the fetch call for the school list from api
        viewModel.getHighSchoolList()

        // on click listener for refresh button to fetch school list from api
        binding?.highSchoolRefreshButton?.setOnClickListener {
            viewModel.getHighSchoolList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
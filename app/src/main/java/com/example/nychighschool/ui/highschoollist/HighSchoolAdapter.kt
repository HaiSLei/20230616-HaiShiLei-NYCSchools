package com.example.nychighschool.ui.highschoollist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nychighschool.databinding.ListItemHighSchoolBinding
import com.example.nychighschool.models.HighSchool

class HighSchoolAdapter(private val onSchoolClicked : (school : HighSchool) -> Unit, callback: HighSchoolItemCallback = HighSchoolItemCallback()):
    ListAdapter<HighSchool, HighSchoolAdapter.HighSchoolViewHolder>(callback) {

    class HighSchoolItemCallback: DiffUtil.ItemCallback<HighSchool>() {

        override fun areItemsTheSame(oldItem: HighSchool, newItem: HighSchool): Boolean {
            return oldItem.dbn == newItem.dbn
        }

        override fun areContentsTheSame(oldItem: HighSchool, newItem: HighSchool): Boolean {
            return oldItem == newItem
        }
    }

    inner class HighSchoolViewHolder(private val binding: ListItemHighSchoolBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // binding the high school details for each item
            fun bind(highSchool: HighSchool) {
                binding.listItemHighSchoolNameTextView.text = highSchool.schoolName
                val address = highSchool.schoolAddress + ", " + highSchool.city + ", " + highSchool.State + ", " + highSchool.zip
                binding.listItemHighSchoolAddressTextView.text = address
                binding.listItemHighSchoolPhoneTextView.text = highSchool.phone

                // listener for on click for the high school item and navigate to SAT detail fragment
                binding.root.setOnClickListener {
                    onSchoolClicked(highSchool)
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighSchoolViewHolder {
        val binding = ListItemHighSchoolBinding.inflate(
            LayoutInflater.from(parent.context),parent,
            false)
        return HighSchoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HighSchoolViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}
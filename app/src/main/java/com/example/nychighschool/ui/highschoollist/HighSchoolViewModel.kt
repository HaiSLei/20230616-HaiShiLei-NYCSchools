package com.example.nychighschool.ui.highschoollist

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nychighschool.models.HighSchool
import com.example.nychighschool.repositories.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HighSchoolViewModel @Inject constructor( private val schoolRepo : SchoolRepository) : ViewModel() {

    private val _schoolListResult: MutableLiveData<Response<List<HighSchool>>> by lazy {
        MutableLiveData()
    }

    val schoolListResult: LiveData<Response<List<HighSchool>>> = _schoolListResult

    fun getHighSchoolList() {

        //launch in IO dispatcher to fetch the suspend school list api
        viewModelScope.launch(Dispatchers.IO) {
            // try and catch the SAT api for issues. Using postValue(happens in main dispatcher) to set the _schoolResult
            try {
                val response = schoolRepo.getHighSchools()
                _schoolListResult.postValue(response)
            } catch(e: Exception) {
                val responseBody = e.message?.toResponseBody() ?: "unknown error".toResponseBody()
                Log.e(TAG, "getHighSchoolList: $responseBody")
                _schoolListResult.postValue(Response.error(900, responseBody))
            }
        }
    }
}
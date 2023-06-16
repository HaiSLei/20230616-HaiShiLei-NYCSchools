package com.example.nychighschool.ui.satInfo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nychighschool.models.SchoolSatInfo
import com.example.nychighschool.repositories.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SatInfoViewModel @Inject constructor(private val schoolRepo : SchoolRepository): ViewModel() {

    private val _satResult: MutableLiveData<Response<List<SchoolSatInfo>>> by lazy {
        MutableLiveData()
    }

    val satResult: LiveData<Response<List<SchoolSatInfo>>> = _satResult

    fun getSatInfo(dbn : String) {

        //launch in IO dispatcher to fetch the suspend SAT api
        viewModelScope.launch(Dispatchers.IO) {
            // try and catch the SAT api for issues. Using postValue(happens in main dispatcher) to set the _satResult
            try {
                val response = schoolRepo.getSAT(dbn)
                _satResult.postValue(response)
            } catch(e: Exception) {
                val responseBody = e.message?.toResponseBody() ?: "unknown error".toResponseBody()
                Log.e(TAG, "getHighSchoolList: $responseBody")
                _satResult.postValue(Response.error(900, responseBody))
            }
        }
    }
}
package com.example.nychighschool.api

import com.example.nychighschool.models.HighSchool
import com.example.nychighschool.models.SchoolSatInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET("resource/s3k6-pzi2.json")
    suspend fun getHighSchools() : Response<List<HighSchool>>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSatInfo(@Query("dbn") dbn: String ) : Response<List<SchoolSatInfo>>

}




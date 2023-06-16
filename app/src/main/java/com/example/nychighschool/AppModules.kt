package com.example.nychighschool

import com.example.nychighschool.api.ApiService
import com.example.nychighschool.repositories.SchoolRepository
import com.example.nychighschool.repositories.SchoolRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    @Singleton
    fun apiBuilder() : Retrofit.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://data.cityofnewyork.us/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
    }

    @Provides
    @Singleton
    fun providesAPI(apiBuilder: Retrofit.Builder) : ApiService {
        return apiBuilder.build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSchoolRepository(api : ApiService) : SchoolRepository = SchoolRepositoryImp(api)

}

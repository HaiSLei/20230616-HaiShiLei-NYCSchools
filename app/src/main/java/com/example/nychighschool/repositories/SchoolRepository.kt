package com.example.nychighschool.repositories


import com.example.nychighschool.api.ApiService
import com.example.nychighschool.models.HighSchool
import com.example.nychighschool.models.SchoolSatInfo
import retrofit2.Response
import javax.inject.Inject

interface SchoolRepository {
    suspend fun getHighSchools(): Response<List<HighSchool>>
    suspend fun getSAT(dbn: String) : Response<List<SchoolSatInfo>>
}

class SchoolRepositoryImp @Inject constructor(private val api : ApiService) : SchoolRepository {

    override suspend fun getHighSchools(): Response<List<HighSchool>> {
        return api.getHighSchools()
    }

    override suspend fun getSAT(dbn: String) : Response<List<SchoolSatInfo>>{
        return api.getSatInfo(dbn)
    }
}
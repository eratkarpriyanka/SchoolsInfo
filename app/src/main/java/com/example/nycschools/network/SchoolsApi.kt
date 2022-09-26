package com.example.nycschools.network

import com.example.nycschools.models.SchoolListData
import com.example.nycschools.models.SchoolListDataItem
import retrofit2.Response
import retrofit2.http.GET

interface SchoolsApi {

    /**
     * getter api call to retrieve the school info
     */
    @GET("s3k6-pzi2.json")
    suspend fun getSchoolList(): Response<List<SchoolListDataItem>>
}

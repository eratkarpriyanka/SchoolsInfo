package com.example.nycschools.network

import com.example.nycschools.models.SchoolDetailsDataItem
import com.example.nycschools.models.SchoolListDataItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolsApi {

    /**
     * getter api call to retrieve the school list
     */
    @GET("s3k6-pzi2.json")
    suspend fun getSchoolList(): Response<List<SchoolListDataItem>>

    /***
     * getter api call to retrieve the school details for school id
     */
    @GET("f9bf-2cp4.json")
    suspend fun getSchoolDetails(@Query("dbn") id: String): Response<List<SchoolDetailsDataItem>>
}

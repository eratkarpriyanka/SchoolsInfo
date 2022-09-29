package com.example.nycschools

import android.util.Log
import com.example.nycschools.models.RepositoryResult
import com.example.nycschools.models.SchoolDetailsData
import com.example.nycschools.models.SchoolDetailsDataItem
import com.example.nycschools.models.SchoolListDataItem
import com.example.nycschools.network.SchoolsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Repository that provides required School data
 */
interface SchoolRepository {

    /**
     * Getter function that returns a flow that emits the School data.
     */
    fun retrieveListOfSchools(): Flow<RepositoryResult<List<SchoolListDataItem>>>

    fun retrieveSchoolDetails(id: String): Flow<RepositoryResult<List<SchoolDetailsDataItem>>>
}

class SchoolRepositoryImpl @Inject constructor(private val schoolsApi: SchoolsApi) :
    SchoolRepository {
    override fun retrieveListOfSchools(): Flow<RepositoryResult<List<SchoolListDataItem>>> =
        flow {

            Log.d(
                SchoolRepository::class.simpleName,
                "SchoolList: fetching schools preview data"
            )
            val response = schoolsApi.getSchoolList()

            response.takeIf {
                it.isSuccessful && (it.body() != null)
            }?.let {
                emit(RepositoryResult.Success(it.body()!!))
                Log.d(
                    SchoolRepository::class.simpleName,
                    "SchoolList: Success!!! fetching schools preview data"
                )

            } ?: kotlin.run {
                emit(RepositoryResult.Error(response.errorBody().toString()))
            }

        }.flowOn(Dispatchers.IO)


    override fun retrieveSchoolDetails(id: String): Flow<RepositoryResult<List<SchoolDetailsDataItem>>> =
        flow {

            Log.d(
                SchoolRepository::class.simpleName,
                "SchoolDetails: fetching schools preview data"
            )
            val response = schoolsApi.getSchoolDetails(id)

            response.takeIf {
                it.isSuccessful && (it.body() != null)
            }?.let {
                emit(RepositoryResult.Success(it.body()!!))
                Log.d(
                    SchoolRepository::class.simpleName,
                    "SchoolDetails: Success!!! fetching school details preview data"
                )

            } ?: kotlin.run {
                emit(RepositoryResult.Error(response.errorBody().toString()))
            }

        }.flowOn(Dispatchers.IO)


}
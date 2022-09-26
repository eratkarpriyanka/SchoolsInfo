package com.example.nycschools

import android.util.Log
import com.example.nycschools.models.RepositoryResult
import com.example.nycschools.models.SchoolListDataItem
import com.example.nycschools.network.SchoolsApi
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class SchoolsRepositoryImplTest {

    @Test
    fun `verify all schools repository call`() = TestScope().runTest {

        mockkStatic(Log::class)

        val mockSchoolsApi = mockk<SchoolsApi>(relaxed = true)
        val schoolRepository = SchoolRepositoryImpl(mockSchoolsApi)
        val data = mockk<List<SchoolListDataItem>>(relaxed = true)

        coEvery { mockSchoolsApi.getSchoolList().body() } returns data

        schoolRepository.retrieveListOfSchools().collect { result ->
            if (result is RepositoryResult.Success) {
                assertEquals(result.data, RepositoryResult.Success(data))
            }
        }
    }
}
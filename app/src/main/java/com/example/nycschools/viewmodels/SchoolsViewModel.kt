package com.example.nycschools.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschools.SchoolRepository
import com.example.nycschools.models.RepositoryResult
import com.example.nycschools.models.SchoolDetailsDataItem
import com.example.nycschools.models.SchoolListDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(private val schoolRepository: SchoolRepository) :
    ViewModel() {

    private val _liststate =
        MutableStateFlow<SchoolApiResponseState>(SchoolApiResponseState.Loading)
    val listState = _liststate.asStateFlow()

    private val _detailsState =
        MutableStateFlow<SchoolApiResponseState>(SchoolApiResponseState.Loading)
    val detailsState = _detailsState.asStateFlow()

    /**
     * make an api call to request school list
     */
    fun loadSchools() {
        viewModelScope.launch {
            _liststate.value =
                SchoolApiResponseState.Loading //reset the response state to default mode as loading
            schoolRepository.retrieveListOfSchools().catch { e ->
                e.cause?.let {
                    _liststate.value = SchoolApiResponseState.Error(it.localizedMessage ?: "")
                    Log.d(
                        SchoolsViewModel::class.simpleName,
                        "viewmodel error occurred while fetching list response ${it.localizedMessage}"
                    )
                }
            }.collect { response ->
                when (response) {
                    is RepositoryResult.Success -> {
                        Log.d(
                            SchoolsViewModel::class.simpleName,
                            "viewmodel success fetching list response ${response.data}"
                        )

                        _liststate.value = SchoolApiResponseState.Success(response.data)
                    }

                    is RepositoryResult.Error -> {
                        Log.d(
                            SchoolsViewModel::class.simpleName,
                            "viewmodel error occurred while fetching list response ${response.errorString}"
                        )
                        _liststate.value = SchoolApiResponseState.Error(response.errorString)
                    }
                }
            }
        }
    }


    /**
     * Loading details of a school
     */
    fun loadSchoolDetails(schoolId: String) {
        viewModelScope.launch {
            _detailsState.value =
                SchoolApiResponseState.Loading //reset the response state to default mode as loading
            schoolRepository.retrieveSchoolDetails(schoolId).catch { e ->
                e.cause?.let {
                    _detailsState.value = SchoolApiResponseState.Error(it.localizedMessage ?: "")
                    Log.d(
                        SchoolsViewModel::class.simpleName,
                        "viewmodel error occurred while fetching details response ${it.localizedMessage}"
                    )
                }
            }.collect { response ->

                when (response) {
                    is RepositoryResult.Success -> {
                        if (response.data.isNotEmpty()) {
                            Log.d(
                                SchoolsViewModel::class.simpleName,
                                "viewmodel success fetching details response ${response.data}"
                            )
                            _detailsState.value =
                                SchoolApiResponseState.SuccessDetails(response.data[0])

                        } else {
                            Log.d(
                                SchoolsViewModel::class.simpleName,
                                "viewmodel error occurred no school details available "
                            )
                            _detailsState.value =
                                SchoolApiResponseState.Error("No details available")
                        }
                    }
                    is RepositoryResult.Error -> {
                        Log.d(
                            SchoolsViewModel::class.simpleName,
                            "viewmodel error occurred while fetching details response ${response.errorString}"
                        )
                        _detailsState.value = SchoolApiResponseState.Error(response.errorString)
                    }
                }
            }
        }
    }
}

/**
 * Type of response statuses
 */
sealed class SchoolApiResponseState {
    object Loading : SchoolApiResponseState()
    data class Success(val data: List<SchoolListDataItem>) :
        SchoolApiResponseState()

    data class SuccessDetails(val data: SchoolDetailsDataItem) :
        SchoolApiResponseState()

    class Error(val errorStringRes: String) : SchoolApiResponseState()
}
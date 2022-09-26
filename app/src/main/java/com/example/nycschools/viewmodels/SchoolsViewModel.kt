package com.example.nycschools.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nycschools.SchoolRepository
import com.example.nycschools.models.RepositoryResult
import com.example.nycschools.models.SchoolListDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(private val schoolRepository: SchoolRepository) :
    ViewModel() {

    private val _state = MutableStateFlow<SchoolListState>(SchoolListState.Loading)
    val state = _state.asStateFlow()

    /**
     * make an api call to request school list
     */
    suspend fun loadSchools() {
        _state.value = SchoolListState.Loading //reset the response state to default mode as loading

        schoolRepository.retrieveListOfSchools().catch { e ->
            e.cause?.let {
                _state.value = SchoolListState.Error(it.localizedMessage ?: "")
                Log.d(
                    SchoolsViewModel::class.simpleName,
                    "viewmodel error occurred while fetching response ${it.localizedMessage}"
                )
            }
        }.collect { response ->
            when (response) {
                is RepositoryResult.Success -> {
                    Log.d(
                        SchoolsViewModel::class.simpleName,
                        "viewmodel success fetching response ${response.data}"
                    )

                    _state.value = SchoolListState.Success(response.data)
                }

                is RepositoryResult.Error -> {
                    Log.d(
                        SchoolsViewModel::class.simpleName,
                        "viewmodel error occurred while fetching response ${response.errorString}"
                    )
                    _state.value = SchoolListState.Error(response.errorString)
                }
            }

        }
    }
}

/**
 * Type of response statuses
 */
sealed class SchoolListState {
    object Loading : SchoolListState()
    data class Success(val data: List<SchoolListDataItem>) :
        SchoolListState()

    class Error(val errorStringRes: String) : SchoolListState()
}
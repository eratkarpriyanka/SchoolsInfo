package com.example.nycschools.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschools.SchoolRepository
import com.example.nycschools.models.RepositoryResult
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

    val state = mutableStateOf<SchoolListState>(SchoolListState.Loading)

    /**
     * make an api call to request school list
     */
    fun loadSchools() {
        viewModelScope.launch {
            state.value =
                SchoolListState.Loading //reset the response state to default mode as loading
            schoolRepository.retrieveListOfSchools().catch { e ->
                e.cause?.let {
                    state.value = SchoolListState.Error(it.localizedMessage ?: "")
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

                        state.value = SchoolListState.Success(response.data)
                    }

                    is RepositoryResult.Error -> {
                        Log.d(
                            SchoolsViewModel::class.simpleName,
                            "viewmodel error occurred while fetching response ${response.errorString}"
                        )
                        state.value = SchoolListState.Error(response.errorString)
                    }
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
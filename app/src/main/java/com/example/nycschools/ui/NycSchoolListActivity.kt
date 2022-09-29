package com.example.nycschools.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.nycschools.R
import com.example.nycschools.ui.compose.CircularProgressBar
import com.example.nycschools.ui.compose.SchoolDetailsEmptyUI
import com.example.nycschools.ui.compose.SchoolDetailsUI
import com.example.nycschools.ui.compose.SchoolsListUI
import com.example.nycschools.viewmodels.SchoolApiResponseState
import com.example.nycschools.viewmodels.SchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NycSchoolListActivity : AppCompatActivity(), LifecycleOwner {

    private val viewModel: SchoolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoadList()
        }

        retrieveListInfo()
    }

    private fun retrieveListInfo() {
        // launches coroutine without blocking the UI thread
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Invokes the Schools API anytime the activity is started
                viewModel.loadSchools()
            }
        }
    }

    /**
     * observe the school list @see [SchoolsViewModel.listState] state flow
     */
    @Preview
    @Composable
    private fun LoadList() {

        /* recompose jetpack compose ui based on changes to viewModel.state */
        val stateFlow = viewModel.listState.collectAsState()
        when (val state = stateFlow.value) {
            is SchoolApiResponseState.Loading -> {
                Log.d(NycSchoolListActivity::class.simpleName, "loading the list")
                CircularProgressBar() // show circular progress
            }
            is SchoolApiResponseState.Error -> {
                Log.d(
                    NycSchoolListActivity::class.simpleName,
                    "error occurred loading the list ${state.errorStringRes}"
                )
                // display error message
                Toast.makeText(
                    this,
                    stringResource(R.string.error_loading_list),
                    Toast.LENGTH_SHORT
                ).show()
                SchoolDetailsEmptyUI() // show error UI
            }
            is SchoolApiResponseState.Success -> {
                Log.d(
                    NycSchoolListActivity::class.simpleName,
                    "successfully fetched the list. Details of 1st school ${state.data[0].school_name}"
                )
                // populate the school list
                SchoolsListUI(state.data, viewModel)
            }
            else -> {
                /* Do nothing, this case is due to 2 success states in @see [SchoolsViewModel.SchoolApiResponseState]
                *  */
            }

        }
    }
}


package com.example.nycschools.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.nycschools.ui.compose.CircularProgressBar
import com.example.nycschools.ui.compose.SchoolsListUI
import com.example.nycschools.viewmodels.SchoolListState
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
     * observe the school list @see [SchoolsViewModel.state] state flow
     */
    @Preview
    @Composable
    private fun LoadList() {


        // Initializes schools state flow observer
        when (val state = viewModel.state.value) {
            is SchoolListState.Loading -> {
                Log.d(NycSchoolListActivity::class.simpleName, "loading the list")
                CircularProgressBar()
            }
            is SchoolListState.Error -> {
                Log.d(
                    NycSchoolListActivity::class.simpleName,
                    "error occurred loading the list ${state.errorStringRes}"
                )
                Toast.makeText(this, "There was an issue loading the list", Toast.LENGTH_SHORT)
                    .show()
            }
            is SchoolListState.Success -> {
                Log.d(
                    NycSchoolListActivity::class.simpleName,
                    "successfully fetched the list 1st school ${state.data[0].school_name}"
                )

                SchoolsListUI(state.data)
            }

        }
    }
}


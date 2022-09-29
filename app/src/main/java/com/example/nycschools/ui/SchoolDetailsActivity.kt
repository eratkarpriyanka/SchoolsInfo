package com.example.nycschools.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.nycschools.viewmodels.SchoolApiResponseState
import com.example.nycschools.viewmodels.SchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolDetailsActivity : AppCompatActivity(), LifecycleOwner {

    private val viewModel: SchoolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoadSchoolDetails()
        }
        val schoolId = intent.getStringExtra("SCHOOL_ID")
        schoolId?.let {
            retrieveDetailstInfo(it)
        } ?: kotlin.run {
            Toast.makeText(this, "Some issue occurred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun retrieveDetailstInfo(schoolId: String) {
        // launches coroutine without blocking the UI thread
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Invokes the Schools API anytime the activity is started
                viewModel.loadSchoolDetails(schoolId)
            }
        }
    }

    @Preview
    @Composable
    fun LoadSchoolDetails() {
        val stateFlow = viewModel.detailsState.collectAsState()
        val context = LocalContext.current

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                /**
                 * observe the school list @see [SchoolsViewModel.detailsState] state flow
                 * /recompose jetpack compose ui based on changes to viewModel.state
                 */
                when (val state = stateFlow.value) {
                    is SchoolApiResponseState.Loading -> {
                        Log.d("SchoolDetailsUI", "loading the list")
                        CircularProgressBar() // show circular progress
                    }
                    is SchoolApiResponseState.Error -> {
                        Log.d(
                            "SchoolDetailsUI",
                            "error occurred loading the list ${state.errorStringRes}"
                        )
                        // display error message
                        Toast.makeText(
                            context,
                            stringResource(R.string.error_loading_details),
                            Toast.LENGTH_SHORT
                        ).show()
                        SchoolDetailsEmptyUI() // show empty details UI
                    }

                    is SchoolApiResponseState.SuccessDetails -> {
                        Log.d(
                            "SchoolDetailsUI",
                            "successfully fetched the details ${state.data.school_name}"
                        )
                        SchoolDetailsUI(state.data) // populate details UI
                    }
                    else -> {
                        /* Do nothing, this additional case is due to 2 success states in @see [SchoolsViewModel.SchoolApiResponseState]
                           *  */
                    }
                }

            }
        }

    }
}
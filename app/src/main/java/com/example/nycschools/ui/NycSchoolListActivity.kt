package com.example.nycschools.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.nycschools.R
import com.example.nycschools.viewmodels.SchoolListState
import com.example.nycschools.viewmodels.SchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NycSchoolListActivity : AppCompatActivity(), LifecycleOwner {

    private val viewModel: SchoolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nyc_school_list)

        initObservers()
    }

    /**
     * observe the school list @see [SchoolsViewModel.state] state flow
     */
    private fun initObservers() {
        // launches coroutine without blocking the UI thread
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Invokes the Schools API anytime the activity is started
                viewModel.loadSchools()

                // Initializes schools state flow observer
                viewModel.state.collect { state ->

                    when (state) {
                        is SchoolListState.Loading -> {
                            Log.d(NycSchoolListActivity::class.simpleName, "loading the list")
                        }
                        is SchoolListState.Error -> {
                            Log.d(
                                NycSchoolListActivity::class.simpleName,
                                "error occurred loading the list ${state.errorStringRes}"
                            )
                        }
                        is SchoolListState.Success -> {
                            Log.d(
                                NycSchoolListActivity::class.simpleName,
                                "successfully fetched the list 1st school ${state.data[0].school_name}"
                            )
                        }
                    }
                }
            }
        }
    }
}
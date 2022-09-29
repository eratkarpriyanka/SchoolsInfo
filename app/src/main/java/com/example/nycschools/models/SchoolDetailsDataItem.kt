package com.example.nycschools.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model class that holds School Details info
 */
@Parcelize
data class SchoolDetailsDataItem(
    val dbn: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,
    val school_name: String
) : Parcelable
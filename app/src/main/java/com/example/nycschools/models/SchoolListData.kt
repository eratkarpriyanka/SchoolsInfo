package com.example.nycschools.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model class that holds School List info
 */
@Parcelize
class SchoolListData : ArrayList<SchoolListDataItem>(), Parcelable
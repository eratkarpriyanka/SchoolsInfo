package com.example.nycschools.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model class that holds School List Item info
 */
@Parcelize
data class SchoolListDataItem(

    val addtl_info1: String,
    val advancedplacement_courses: String,
    val attendance_rate: String,
    val bbl: String,
    val bin: String,
    val boro: String,
    val borough: String,
    val boys: String,
    val building_code: String,
    val bus: String,
    val campus_name: String,
    val census_tract: String,
    val city: String,
    val college_career_rate: String,
    val council_district: String,
    val dbn: String,
    val diplomaendorsements: String,
    val ell_programs: String,
    val end_time: String,
    val extracurricular_activities: String,
    val fax_number: String,
    val finalgrades: String,
    val geoeligibility: String,
    val girls: String,
    val grades2018: String,
    val graduation_rate: String,
    val language_classes: String,
    val latitude: String,
    val location: String,
    val longitude: String,
    val neighborhood: String,
    val nta: String,
    val overview_paragraph: String,
    val pbat: String,
    val pct_stu_enough_variety: String,
    val pct_stu_safe: String,
    val phone_number: String,
    val primary_address_line_1: String,
    val psal_sports_boys: String,
    val psal_sports_coed: String,
    val psal_sports_girls: String,
    val ptech: String,
    val school_10th_seats: String,
    val school_accessibility_description: String,
    val school_email: String,
    val school_name: String,
    val school_sports: String,
    val shared_space: String,
    val specialized: String,
    val start_time: String,
    val state_code: String,
    val subway: String,
    val total_students: String,
    val transfer: String,
    val website: String,
    val zip: String
) : Parcelable
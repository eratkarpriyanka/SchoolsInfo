package com.example.nycschools.ui.compose

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycschools.R
import com.example.nycschools.models.SchoolDetailsDataItem

@Composable
fun SchoolDetailsUI(schoolInfo: SchoolDetailsDataItem) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                schoolInfo.school_name,
                fontFamily = FontFamily(Typeface.DEFAULT_BOLD),
                fontSize = 22.sp,
                color = colorResource(id = R.color.purple_500)
            )

            Text(
                "Sat scores are as :",
                fontFamily = FontFamily(Typeface.DEFAULT),
                fontSize = 18.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Math : ${schoolInfo.sat_math_avg_score}",
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .align(Alignment.CenterHorizontally)

                )
                Text(
                    "Reading : ${schoolInfo.sat_critical_reading_avg_score}",
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    "Writing : ${schoolInfo.sat_writing_avg_score}",
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.padding(bottom = 16.dp))
            }
        }
    }
}
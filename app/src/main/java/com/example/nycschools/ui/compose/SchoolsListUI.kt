package com.example.nycschools.ui.compose

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycschools.models.SchoolListDataItem
import com.example.nycschools.ui.SchoolDetailsActivity
import com.example.nycschools.viewmodels.SchoolsViewModel

@Preview
@Composable
fun SchoolsListUI(listSchools: List<SchoolListDataItem>, viewModel: SchoolsViewModel) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Text(
                "Schools in NYC",
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start)
            )

            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxHeight(),
            ) {
                // displays list
                LazyColumn {

                    listSchools.forEach { school ->
                        item {
                            SchoolListItem(school.dbn, school.school_name, viewModel)
                        }
                    }

                }

            }
        }
    }
}


fun launchSchoolDetails(schooldId: String, context: Context) {
    val intent = Intent(context, SchoolDetailsActivity::class.java).apply {
        putExtra("SCHOOL_ID", schooldId)
    }
    context.startActivity(intent)
}


/**
 * List item for a school list
 */
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun SchoolListItem(schooldId: String, schoolName: String, viewModel: SchoolsViewModel) {

    val context = LocalContext.current

    Card(
        elevation = 4.dp,
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable {
                launchSchoolDetails(schooldId, context)
            },
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = schoolName,
                fontFamily = FontFamily(Typeface.SANS_SERIF)
            )
        }
    }
}
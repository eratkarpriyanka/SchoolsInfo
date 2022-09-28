package com.example.nycschools.ui.compose

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycschools.models.SchoolListDataItem

@Preview
@Composable
fun SchoolsListUI(listSchools: List<SchoolListDataItem>) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Text(
                "School List",
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

                LazyColumn {

                    listSchools.forEach { school ->
                        item {
                            SchoolListItems(school.school_name)
                        }
                    }

                }

            }
        }
    }
}

@Preview
@Composable
fun SchoolListItems(schoolName: String) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = schoolName)
        }
    }
}
package com.example.nycschools.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycschools.R

/**
 * On Error show this UI + the Toast
 */
@Composable
fun SchoolDetailsEmptyUI() {
    // Optimize so that error screen or details are loaded within surface
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                stringResource(R.string.details_are_empty), modifier = Modifier
                    .padding(22.dp)
                    .align(Alignment.Center),
                fontSize = 22.sp
            )
        }
    }
}

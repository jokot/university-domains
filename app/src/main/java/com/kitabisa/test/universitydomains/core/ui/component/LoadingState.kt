package com.kitabisa.test.universitydomains.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.kitabisa.test.universitydomains.core.testing.constant.TestTag

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTag.LOADING_STATE),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
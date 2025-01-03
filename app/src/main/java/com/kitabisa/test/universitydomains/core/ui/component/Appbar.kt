package com.kitabisa.test.universitydomains.core.ui.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.kitabisa.test.universitydomains.core.ui.icon.UniversityIcons
import com.kitabisa.test.universitydomains.core.util.IntentUtils
import com.kitabisa.test.universitydomains.core.util.Urls

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Appbar(
    modifier: Modifier = Modifier,
    title: String
) {
    val context = LocalContext.current
    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0, 0, 0, 0),
        title = {
            Text(title, style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            IconButton(onClick = {
                IntentUtils.openUrl(context, Urls.GITHUB_URL)
            }) {
                Icon(
                    imageVector = UniversityIcons.Info,
                    contentDescription = "info"
                )
            }
        }
    )
}
package com.kitabisa.test.universitydomains.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitabisa.test.universitydomains.R
import com.kitabisa.test.universitydomains.core.ui.icon.UniversityIcons
import com.kitabisa.test.universitydomains.core.ui.theme.UniversityDomainsTheme
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AsyncImage(
                    model = Urls.ICON_URL,
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
                Text(title, style = MaterialTheme.typography.titleLarge)
            }
        },
        actions = {
            IconButton(
                onClick = {
                    IntentUtils.openUrl(context, Urls.GITHUB_URL)
                }
            ) {
                Icon(
                    imageVector = UniversityIcons.Info,
                    contentDescription = "info"
                )
            }
        }
    )
}

@Preview
@Composable
fun AppbarPreview() {
    UniversityDomainsTheme {
        Appbar(
            title = stringResource(R.string.feature_home_title)
        )
    }
}
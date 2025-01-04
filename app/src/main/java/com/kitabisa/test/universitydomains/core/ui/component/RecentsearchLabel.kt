package com.kitabisa.test.universitydomains.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitabisa.test.universitydomains.R
import com.kitabisa.test.universitydomains.core.ui.theme.UniversityDomainsTheme

@Composable
fun RecentSearchLabel(
    modifier: Modifier = Modifier,
    onClearClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.feature_search_recent_label),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        TextButton(onClick = onClearClick) {
            Text(stringResource(R.string.feature_search_recent_clear))
        }
    }
}

@Preview
@Composable
private fun RecentSearchLabelPreview() {
    UniversityDomainsTheme {
        RecentSearchLabel {}
    }
}

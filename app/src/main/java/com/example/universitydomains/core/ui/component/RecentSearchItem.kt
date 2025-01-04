package com.example.universitydomains.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.universitydomains.core.testing.data.UniversityTestData
import com.example.universitydomains.core.ui.icon.UniversityIcons
import com.example.universitydomains.core.ui.theme.UniversityDomainsTheme

@Composable
fun RecentSearchItem(
    recent: String,
    onRecentSearchClick: (String) -> Unit,
    onRemoveRecentSearchClick: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRecentSearchClick(recent) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = recent,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        IconButton(
            modifier = Modifier.wrapContentSize(),
            onClick = { onRemoveRecentSearchClick(recent) }) {
            Icon(imageVector = UniversityIcons.Clear, contentDescription = "Remove")
        }
    }
}

@Preview
@Composable
private fun RecentSearchItemPreview() {
    UniversityDomainsTheme {
        RecentSearchItem(
            recent = UniversityTestData.recentSearch.first(),
            onRecentSearchClick = {},
            onRemoveRecentSearchClick = {}
        )
    }
}

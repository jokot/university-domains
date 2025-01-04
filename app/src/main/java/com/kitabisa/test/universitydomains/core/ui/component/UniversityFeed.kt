package com.kitabisa.test.universitydomains.core.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import com.kitabisa.test.universitydomains.core.testing.data.UniversityTestData
import com.kitabisa.test.universitydomains.core.ui.theme.UniversityDomainsTheme

@Composable
fun UniversityFeed(
    modifier: Modifier = Modifier,
    savableUniversities: List<SavableUniversity>,
    onFavoriteClick: (SavableUniversity) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(savableUniversities) { savableUniversity ->
            UniversityItem(
                savableUniversity = savableUniversity,
                onFavoriteClick = onFavoriteClick
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}

@Preview
@Composable
private fun UniversityFeedPreview() {
    UniversityDomainsTheme {
        UniversityFeed(
            savableUniversities = UniversityTestData.savableUniversities
        ) {}
    }
}
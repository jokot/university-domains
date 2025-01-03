package com.kitabisa.test.universitydomains.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kitabisa.test.universitydomains.core.model.SavableUniversity
import com.kitabisa.test.universitydomains.core.ui.icon.UniversityIcons
import com.kitabisa.test.universitydomains.core.util.IntentUtils
import com.kitabisa.test.universitydomains.core.util.countryCodeToEmojiFlag

@Composable
fun UniversityItem(
    modifier: Modifier = Modifier,
    savableUniversity: SavableUniversity,
    onFavoriteClick: (SavableUniversity) -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = savableUniversity.university.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${countryCodeToEmojiFlag(savableUniversity.university.alphaTwoCode)} ${savableUniversity.university.country}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            savableUniversity.university.webPages.forEach { url ->
                Text(
                    text = AnnotatedString(
                        text = url,
                        spanStyles = listOf(
                            AnnotatedString.Range(
                                item = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    textDecoration = TextDecoration.Underline
                                ),
                                start = 0,
                                end = url.length
                            )
                        )
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        IntentUtils.openUrl(context, url)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        IconButton(
            onClick = {
                onFavoriteClick(savableUniversity.copy(isFavorite = !savableUniversity.isFavorite))
            }
        ) {
            Icon(
                imageVector = if (savableUniversity.isFavorite) UniversityIcons.Favorite else UniversityIcons.FavoriteBorder,
                contentDescription = if (savableUniversity.isFavorite) "Unfollow" else "Follow",
                tint = if (savableUniversity.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
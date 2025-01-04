package com.example.universitydomains.core.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent

object IntentUtils {
    private fun launchCustomChromeTab(context: Context, uri: Uri) {
        val chromeTabsIntent = CustomTabsIntent.Builder()
            .build()

        chromeTabsIntent.launchUrl(context, uri)
    }

    fun openUrl(context: Context, url: String) {
        try {
            val uri = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                Uri.parse("https://$url")
            } else {
                Uri.parse(url)
            }
            launchCustomChromeTab(context, uri)
        } catch (e: Exception) {
            // Fallback to default browser if Chrome Custom Tabs fails
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No browser found", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

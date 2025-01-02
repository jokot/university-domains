package com.kitabisa.test.universitydomains.core.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

object DatastoreKey {
    val FAVORITES = stringSetPreferencesKey("favorites")
    val IS_FETCHED = booleanPreferencesKey("is_fetched")
}
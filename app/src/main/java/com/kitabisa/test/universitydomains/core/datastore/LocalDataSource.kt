package com.kitabisa.test.universitydomains.core.datastore

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getFavorites(): Flow<Set<String>>
    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
    fun getIsFetch(): Flow<Boolean>
    suspend fun setDataIsFetch()
}
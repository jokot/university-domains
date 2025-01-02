package com.kitabisa.test.universitydomains.core.data.di

import com.kitabisa.test.universitydomains.core.data.repository.FavoriteRepository
import com.kitabisa.test.universitydomains.core.data.repository.FavoriteRepositoryImpl
import com.kitabisa.test.universitydomains.core.data.repository.HomeRepository
import com.kitabisa.test.universitydomains.core.data.repository.HomeRepositoryImpl
import com.kitabisa.test.universitydomains.core.data.repository.SearchRepository
import com.kitabisa.test.universitydomains.core.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}
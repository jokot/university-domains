package com.example.universitydomains.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.universitydomains.core.database.AppDatabase
import com.example.universitydomains.core.database.dao.RecentSearchDao
import com.example.universitydomains.core.database.dao.UniversityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room
        .databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "university_domains_database"
        )
        .build()

    @Provides
    @Singleton
    fun provideUniversityDao(
        appDatabase: AppDatabase
    ): UniversityDao = appDatabase.universityDao()

    @Provides
    @Singleton
    fun provideRecentSearchDao(
        appDatabase: AppDatabase
    ): RecentSearchDao = appDatabase.recentSearchDao()

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
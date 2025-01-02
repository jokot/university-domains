package com.kitabisa.test.universitydomains.core.datastore.di

import com.kitabisa.test.universitydomains.core.datastore.LocalDataSource
import com.kitabisa.test.universitydomains.core.datastore.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatastoreModule {

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource
}
package com.kitabisa.test.universitydomains.core.network.di

import com.kitabisa.test.universitydomains.core.network.NetworkDataSource
import com.kitabisa.test.universitydomains.core.network.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindNetworkDataSource(
        networkDataSourceImpl: NetworkDataSourceImpl
    ): NetworkDataSource
}
package com.kitabisa.test.universitydomains.core.network

import com.kitabisa.test.universitydomains.core.network.model.NetworkUniversity
import com.kitabisa.test.universitydomains.core.network.service.ApiService
import javax.inject.Inject

interface NetworkDataSource {
    suspend fun getUniversities(): NetworkResult<List<NetworkUniversity>>
    suspend fun searchUniversity(name: String): NetworkResult<List<NetworkUniversity>>
}

class NetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): NetworkDataSource {
    override suspend fun getUniversities(): NetworkResult<List<NetworkUniversity>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchUniversity(name: String): NetworkResult<List<NetworkUniversity>> {
        TODO("Not yet implemented")
    }
}
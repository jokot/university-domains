package com.example.universitydomains.core.network

import com.example.universitydomains.core.network.model.NetworkUniversity
import com.example.universitydomains.core.network.service.ApiService
import javax.inject.Inject

interface NetworkDataSource {
    suspend fun getUniversities(): NetworkResult<List<NetworkUniversity>>
    suspend fun searchUniversity(name: String): NetworkResult<List<NetworkUniversity>>
}

class NetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : NetworkDataSource {
    override suspend fun getUniversities(): NetworkResult<List<NetworkUniversity>> {
        return try {
            val response = apiService.searchUniversities()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.localizedMessage.orEmpty())
        }
    }

    override suspend fun searchUniversity(name: String): NetworkResult<List<NetworkUniversity>> {
        return try {
            val response = apiService.searchUniversities(name)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                NetworkResult.Success(body)
            } else {
                NetworkResult.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.localizedMessage.orEmpty())
        }
    }
}
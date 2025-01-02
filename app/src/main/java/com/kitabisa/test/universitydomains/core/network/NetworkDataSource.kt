package com.kitabisa.test.universitydomains.core.network

import com.kitabisa.test.universitydomains.core.network.model.NetworkUniversity

interface NetworkDataSource {
    suspend fun getUniversities(): NetworkResult<List<NetworkUniversity>>
    suspend fun searchUniversity(name: String): NetworkResult<List<NetworkUniversity>>
}

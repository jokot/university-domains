package com.kitabisa.test.universitydomains.core.testing.datasource

import com.kitabisa.test.universitydomains.core.network.NetworkDataSource
import com.kitabisa.test.universitydomains.core.network.NetworkResult
import com.kitabisa.test.universitydomains.core.network.model.NetworkUniversity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class TestNetworkDataSource : NetworkDataSource {
    private val _networkResultFlow = MutableSharedFlow<NetworkResult<List<NetworkUniversity>>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // Test-only method to emit network results
    fun sendNetworkResult(networkResult: NetworkResult<List<NetworkUniversity>>) {
        _networkResultFlow.tryEmit(networkResult)
    }

    override suspend fun getUniversities(): NetworkResult<List<NetworkUniversity>> {
        // Collect the latest emission from _networkResultFlow
        return _networkResultFlow.first()
    }

    override suspend fun searchUniversity(name: String): NetworkResult<List<NetworkUniversity>> {
        // Simulate a filtered search result based on the name
        val allResults =
            _networkResultFlow.replayCache.firstOrNull() ?: NetworkResult.Success(emptyList())

        return if (allResults is NetworkResult.Success) {
            val filteredResults =
                allResults.data.filter { it.name.orEmpty().contains(name, ignoreCase = true) }
            NetworkResult.Success(filteredResults)
        } else {
            allResults
        }
    }
}

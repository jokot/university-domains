package com.kitabisa.test.universitydomains.core.network.service

import com.kitabisa.test.universitydomains.core.network.model.NetworkUniversity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun searchUniversities(
        @Query("name") name: String? = null,
        @Query("country") country: String = "indonesia"
    ): Response<List<NetworkUniversity>>

}
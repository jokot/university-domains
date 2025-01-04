package com.example.universitydomains.core.network.model

import com.example.universitydomains.core.model.University
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUniversity(
    @SerialName("alpha_two_code")
    val alphaTwoCode: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("domains")
    val domains: List<String>? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("web_pages")
    val webPages: List<String>? = null
)

fun NetworkUniversity.toDomain() = University(
    alphaTwoCode = alphaTwoCode.orEmpty(),
    country = country.orEmpty(),
    domains = domains.orEmpty(),
    name = name.orEmpty(),
    webPages = webPages.orEmpty()
)
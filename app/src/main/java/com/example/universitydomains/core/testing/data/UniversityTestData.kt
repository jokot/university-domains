package com.example.universitydomains.core.testing.data

import com.example.universitydomains.core.model.SavableUniversity
import com.example.universitydomains.core.network.model.NetworkUniversity
import com.example.universitydomains.core.network.model.toDomain

object UniversityTestData {
    val networkUniversities = listOf(
        NetworkUniversity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("nusaputra.ac.id"),
            name = "Universitas Nusa Putra",
            webPages = listOf("https://nusaputra.ac.id")
        ),
        NetworkUniversity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("akfarmitseda.ac.id"),
            name = "Akademi Farmasi Mitra Sehat Mandiri Sidoarjo",
            webPages = listOf("http://www.akfarmitseda.ac.id/")
        ),
        NetworkUniversity(
            alphaTwoCode = "ID",
            country = "Indonesia",
            domains = listOf("ugm.ac.id"),
            name = "Universitas Gadjah Mada",
            webPages = listOf("http://www.ugm.ac.id/")
        )
    )

    val universities = networkUniversities.map(NetworkUniversity::toDomain)

    val savableUniversities = universities.map {
        SavableUniversity(
            university = it,
            isFavorite = false
        )
    }

    val favoriteSavableUniversities =
        savableUniversities.map { it.copy(isFavorite = true) }.subList(0, 2)

    val favorites = favoriteSavableUniversities.map { it.university.name }

    const val errorMessage = "Error Test Message"

    val query = universities.first().name

    const val unknown = "unknown"

    val recentSearch = universities.map {
        it.name
    }

    val universitiesFilterByQuery = savableUniversities.filter {
        it.university.name == query
    }
}
package com.kitabisa.test.universitydomains.core.testing.data

import com.kitabisa.test.universitydomains.core.network.model.NetworkUniversity
import com.kitabisa.test.universitydomains.core.network.model.toDomain

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
}
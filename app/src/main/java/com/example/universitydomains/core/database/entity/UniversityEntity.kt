package com.example.universitydomains.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.universitydomains.core.model.University

@Entity(tableName = "university")
data class UniversityEntity(
    val alphaTwoCode: String,
    val country: String,
    val domains: List<String>,

    @PrimaryKey
    val name: String,
    val webPages: List<String>,
    val timestamp: Long = System.currentTimeMillis()
)

fun UniversityEntity.toDomain() = University(
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)

fun University.toEntity() = UniversityEntity(
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)
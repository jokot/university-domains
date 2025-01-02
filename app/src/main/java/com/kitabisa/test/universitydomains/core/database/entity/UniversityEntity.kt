package com.kitabisa.test.universitydomains.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kitabisa.test.universitydomains.core.model.University

@Entity(tableName = "university")
data class UniversityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val alphaTwoCode: String,
    val country: String,
    val domains: List<String>,
    val name: String,
    val webPages: List<String>
)

fun UniversityEntity.toDomain() = University(
    id = id,
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)

fun University.toEntity() = UniversityEntity(
    id = id,
    alphaTwoCode = alphaTwoCode,
    country = country,
    domains = domains,
    name = name,
    webPages = webPages
)
package com.kitabisa.test.universitydomains.core.testing.dao

import com.kitabisa.test.universitydomains.core.database.dao.UniversityDao
import com.kitabisa.test.universitydomains.core.database.entity.UniversityEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class TestUniversityDao : UniversityDao {

    private val _localUniversitiesFlow = MutableStateFlow<List<UniversityEntity>>(emptyList())

    override fun getUniversities(): Flow<List<UniversityEntity>> = _localUniversitiesFlow

    override fun getUniversitiesByName(query: String): Flow<List<UniversityEntity>> {
        return _localUniversitiesFlow.map { universities ->
            universities.filter { entity ->
                entity.name.contains(query, ignoreCase = true) ||
                        entity.domains.any { domain -> domain.contains(query, ignoreCase = true) }
            }
        }
    }

    override suspend fun insertUniversities(universities: List<UniversityEntity>) {
        val current = _localUniversitiesFlow.value.toMutableList()

        universities.forEach { newEntity ->
            // Use `name` as the unique identifier for matching entities
            val index = current.indexOfFirst { it.name == newEntity.name }
            if (index >= 0) {
                // Update the existing entity
                current[index] = newEntity
            } else {
                // Add the new entity
                current.add(newEntity)
            }
        }
        _localUniversitiesFlow.value = current
    }
}

package com.example.universitydomains.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.universitydomains.core.database.entity.UniversityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversityDao {

    @Query("SELECT * FROM university ORDER BY name")
    fun getUniversities(): Flow<List<UniversityEntity>>

    @Query(
        """
        SELECT * FROM university 
        WHERE name LIKE '%' || :query || '%' COLLATE NOCASE 
           OR domains LIKE '%' || :query || '%' COLLATE NOCASE
    """
    )
    fun getUniversitiesByName(query: String): Flow<List<UniversityEntity>>

    @Query("SELECT * FROM university WHERE name IN (:names)")
    suspend fun getUniversitiesByNames(names: List<String>): List<UniversityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversities(universities: List<UniversityEntity>)
}
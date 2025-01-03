package com.kitabisa.test.universitydomains.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kitabisa.test.universitydomains.core.database.entity.UniversityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversityDao {

    @Query("SELECT * FROM university ORDER BY id")
    fun getUniversities(): Flow<List<UniversityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUniversities(users: List<UniversityEntity>)
}
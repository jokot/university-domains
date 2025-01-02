package com.kitabisa.test.universitydomains.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kitabisa.test.universitydomains.core.database.dao.UniversityDao
import com.kitabisa.test.universitydomains.core.database.entity.UniversityEntity

@Database(
    entities = [UniversityEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun universityDao(): UniversityDao
}
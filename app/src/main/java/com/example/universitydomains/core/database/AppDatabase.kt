package com.example.universitydomains.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.universitydomains.core.database.dao.RecentSearchDao
import com.example.universitydomains.core.database.dao.UniversityDao
import com.example.universitydomains.core.database.entity.RecentSearchEntity
import com.example.universitydomains.core.database.entity.UniversityEntity

@Database(
    entities = [
        UniversityEntity::class,
        RecentSearchEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun universityDao(): UniversityDao
    abstract fun recentSearchDao(): RecentSearchDao
}
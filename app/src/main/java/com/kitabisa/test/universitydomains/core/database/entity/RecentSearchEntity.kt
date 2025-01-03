package com.kitabisa.test.universitydomains.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recent_searches")
data class RecentSearchEntity(
    @PrimaryKey val name: String,
    val timeStamp: Long = System.currentTimeMillis()
)

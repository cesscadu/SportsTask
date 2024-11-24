package com.kaizen.gaming.task.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class EventEntity(
    @PrimaryKey val id: String,
    val shortDescription: String,
    val timestamp: Long
)

package com.kaizen.gaming.task.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaizen.gaming.task.data.database.dao.FavoritesDao
import com.kaizen.gaming.task.data.database.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
abstract class SportsDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
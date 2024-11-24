package com.kaizen.gaming.task.data.database.dao

import androidx.room.*
import com.kaizen.gaming.task.data.database.entity.EventEntity

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: EventEntity)

    @Delete
    suspend fun removeFavorite(event: EventEntity)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<EventEntity>

    @Query("SELECT * FROM favorites WHERE id = :eventId LIMIT 1")
    suspend fun getFavorite(eventId: String): EventEntity?
}

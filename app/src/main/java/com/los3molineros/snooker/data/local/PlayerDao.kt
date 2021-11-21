package com.los3molineros.snooker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.los3molineros.snooker.data.model.PlayerEntity

@Dao
interface PlayerDao {
    @Query("SELECT * FROM PlayerEntity WHERE id = :id")
    suspend fun getPlayer(id: Int): PlayerEntity?

    @Query("SELECT COUNT(*) FROM PlayerEntity")
    suspend fun getCountPlayers(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlayer(playerEntity: PlayerEntity)
}
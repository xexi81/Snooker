package com.los3molineros.snooker.data.local

import com.los3molineros.snooker.data.model.PlayerEntity

class LocalPlayerDataSource(private val playerDao: PlayerDao) {
    suspend fun getPlayer(id: Int): PlayerEntity? {
        return playerDao.getPlayer(id)
    }

    suspend fun savePlayer(playerEntity: PlayerEntity) {
        playerDao.savePlayer(playerEntity)
    }

    suspend fun countPlayers(): Int {
        return playerDao.getCountPlayers()
    }
}
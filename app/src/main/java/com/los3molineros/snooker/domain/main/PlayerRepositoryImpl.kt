package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.common.CommonFunctions
import com.los3molineros.snooker.data.model.PlayerEntity
import com.los3molineros.snooker.data.model.toPlayerEntityList
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource

class PlayerRepositoryImpl(
    private val remoteDataSource: RemotePlayerDataSource
) : PlayerRepository {
    override suspend fun getPlayer(id: Int): PlayerEntity? {
        if (!CommonFunctions.isNetworkAvailable()) {
            throw InternalError("No connexion to internet")
        }

        val playerList = remoteDataSource.getPlayerById(id).toPlayerEntityList()
        if (playerList.isEmpty()) {
            throw InternalError("No data")
        }

        return playerList[0]
    }
}
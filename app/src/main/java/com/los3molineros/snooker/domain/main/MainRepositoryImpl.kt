package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.common.CommonFunctions
import com.los3molineros.snooker.common.CommonFunctions.debugLog
import com.los3molineros.snooker.common.CommonFunctions.getCurrentSeason
import com.los3molineros.snooker.data.local.LocalPlayerDataSource
import com.los3molineros.snooker.data.model.toPlayerEntityList
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource

class MainRepositoryImpl(
    private val remoteDataSource: RemotePlayerDataSource,
    private val localDataSource: LocalPlayerDataSource
) : MainRepository {
    override suspend fun getPlayers(): Boolean {
        try {
            if (CommonFunctions.isNetworkAvailable()) {
                val playerList = remoteDataSource.getPlayers(getCurrentSeason())
                    .toPlayerEntityList()

                for (player in playerList) {
                    localDataSource.savePlayer(player)
                }
                return true
            } else {
                debugLog(description = "no internet")
                return false
            }
        } catch (e: IllegalStateException) {
            val playerList = remoteDataSource.getPlayers(getCurrentSeason() - 1)
                .toPlayerEntityList()

            for (player in playerList) {
                localDataSource.savePlayer(player)
            }
            return true
        }
    }
}
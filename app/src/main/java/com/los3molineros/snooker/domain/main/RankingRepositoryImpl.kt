package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.common.CommonFunctions
import com.los3molineros.snooker.data.local.LocalPlayerDataSource
import com.los3molineros.snooker.data.model.RankingResponse
import com.los3molineros.snooker.data.model.RankingUI
import com.los3molineros.snooker.data.model.toPlayerEntity
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource
import com.los3molineros.snooker.data.remote.RemoteRankingDataSource

class RankingRepositoryImpl(private val remoteDataSource: RemoteRankingDataSource,
                            private val localPlayerDataSource: LocalPlayerDataSource,
                            private val remotePlayerDataSource: RemotePlayerDataSource
): RankingRepository {
    override suspend fun getRanking(type: String): List<RankingUI> {
        val rankingList: MutableList<RankingUI> = mutableListOf()
        var rankingResponseList: List<RankingResponse> = listOf()

        // Internet validation
        if (!CommonFunctions.isNetworkAvailable()) {
            throw InternalError("No connexion to internet")
        }

        // Search ranking by type
        try {
            rankingResponseList = remoteDataSource.getRanking(type, CommonFunctions.getCurrentSeason())
        } catch (e: IllegalStateException) {
            rankingResponseList = remoteDataSource.getRanking(type, CommonFunctions.getCurrentSeason() - 1)
        }

        // The API must return results
        if (rankingResponseList.isEmpty()) {
            throw InternalError("No data")
        }

        // Search player info from ranking id players
        for (item in rankingResponseList) {
            var playerName: String? = localPlayerDataSource.getPlayer(item.PlayerID)?.name

            if (playerName==null) {
                try {
                    val playerEntity = remotePlayerDataSource.getPlayerById(item.PlayerID)[0].toPlayerEntity()
                    localPlayerDataSource.savePlayer(playerEntity)
                    playerName = playerEntity.name
                } catch (e: Exception) {
                    playerName = " "
                }
            }

            rankingList.add(RankingUI(item.Position, item.PlayerID, playerName ?: " ", item.Sum))
        }

        return rankingList
    }
}






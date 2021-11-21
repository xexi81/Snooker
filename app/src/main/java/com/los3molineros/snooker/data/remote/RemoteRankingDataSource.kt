package com.los3molineros.snooker.data.remote

import com.los3molineros.snooker.data.model.RankingResponse

class RemoteRankingDataSource(private val webService: WebService) {
    suspend fun getRanking(rt: String, s: Int): List<RankingResponse> {
        return webService.getRanking(rt = rt, s = s)
    }
}
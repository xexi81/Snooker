package com.los3molineros.snooker.data.remote

import com.los3molineros.snooker.data.model.PlayerResponse

class RemotePlayerDataSource(private val webService: WebService) {
    suspend fun getPlayers(s: Int): List<PlayerResponse> {
        return webService.getPlayers(s = s)
    }

    suspend fun getPlayerById(p: Int): List<PlayerResponse> {
        return webService.getPlayerById(p = p)
    }
}
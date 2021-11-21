package com.los3molineros.snooker.data.remote

import com.los3molineros.snooker.data.model.MatchResponse
import com.los3molineros.snooker.data.model.RoundEvent
import com.los3molineros.snooker.data.model.SeasonEventResponse

class RemoteEventsDataSource(private val webService: WebService) {
    suspend fun getSeasonEvents(s: Int): List<SeasonEventResponse> {
        return webService.getSeasonEvents(s = s)
    }

    suspend fun getEvent(e: Int): List<SeasonEventResponse> {
        return webService.getEvent(e = e)
    }

    suspend fun getRoundsEvent(e: Int): List<RoundEvent> {
        return webService.getRoundsEvent(e = e)
    }

    suspend fun getMatchesEvent(e: Int): List<MatchResponse> {
        return webService.getMatchesEvent(e = e)
    }
}
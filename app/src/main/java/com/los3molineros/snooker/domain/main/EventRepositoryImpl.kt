package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.common.CommonFunctions
import com.los3molineros.snooker.data.model.SeasonEventResponse
import com.los3molineros.snooker.data.remote.RemoteEventsDataSource

class EventRepositoryImpl(private val remoteDataSource: RemoteEventsDataSource) : EventRepository {
    override suspend fun getSeasonEvents(): List<SeasonEventResponse> {
        var seasonEventList: List<SeasonEventResponse>

        // Internet validation
        if (!CommonFunctions.isNetworkAvailable()) {
            throw InternalError("No connexion to internet")
        }

        try {
            seasonEventList = remoteDataSource.getSeasonEvents(CommonFunctions.getCurrentSeason()).filter { it.Stage == "F" }
        } catch (e: IllegalStateException) {
            seasonEventList =
                remoteDataSource.getSeasonEvents(CommonFunctions.getCurrentSeason() - 1).filter { it.Stage == "F" }
        }

        if (seasonEventList.isEmpty()) {
            throw InternalError("No data")
        }

        return seasonEventList
    }

}
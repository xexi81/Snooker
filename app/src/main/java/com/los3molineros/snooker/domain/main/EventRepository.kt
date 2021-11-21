package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.data.model.SeasonEventResponse

interface EventRepository {
    suspend fun getSeasonEvents(): List<SeasonEventResponse>
}
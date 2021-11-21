package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.data.model.MatchUI
import com.los3molineros.snooker.data.model.Tournament

interface TournamentRepository {
    suspend fun getTournament(id: Int): Tournament
    suspend fun getMatches(id: Int): List<MatchUI>
}
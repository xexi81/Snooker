package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.data.model.RankingUI

interface RankingRepository {
    suspend fun getRanking(type: String): List<RankingUI>
}
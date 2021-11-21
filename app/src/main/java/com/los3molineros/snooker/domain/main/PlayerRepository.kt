package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.data.model.PlayerEntity

interface PlayerRepository {
    suspend fun getPlayer(id: Int): PlayerEntity?
}
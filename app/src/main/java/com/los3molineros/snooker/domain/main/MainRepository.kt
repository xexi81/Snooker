package com.los3molineros.snooker.domain.main

interface MainRepository {
    suspend fun getPlayers(): Boolean
}
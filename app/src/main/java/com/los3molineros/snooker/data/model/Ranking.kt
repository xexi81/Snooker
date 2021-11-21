package com.los3molineros.snooker.data.model

import androidx.room.Entity

data class RankingResponse (
    val ID: Int = 0,
    val Position: Int = 0,
    val PlayerID: Int = 0,
    val Season: Int = 0,
    val Sum: Int = 0,
    val Type: String = ""
        )


@Entity(primaryKeys = ["season", "type", "position"])
data class RankingEntity (
    val season: Int = 0,
    val type: Int = 0,
    val position: Int = 0,
    val playerId: Int = 0,
    val sum: Int = 0
        )


data class RankingUI(
    val position: Int = 0,
    val playerId: Int = 0,
    val playerName: String = "",
    val sum: Int = 0
)
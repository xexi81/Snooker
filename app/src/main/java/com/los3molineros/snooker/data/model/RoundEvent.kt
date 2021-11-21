package com.los3molineros.snooker.data.model

data class RoundEvent (
    val Round: Int = 0,
    val RoundName: String = "",
    val EventID: Int = 0,
    val MainEvent: Int = 0,
    val Distance: Int = 0,
    val NumLeft: Int = 0,
    val NumMatches: Int = 0,
    val Note: String = "",
    val ValueType: String = "",
    val Rank: Int = 0,
    val Money: Int = 0,
    val SeedGetsHalf: Int = 0,
    val ActualMoney: Int = 0,
    val Currency: String = "",
    val ConversionRate: Int = 0,
    val Points: Int = 0,
    val SeedPoints: Int = 0,
        )
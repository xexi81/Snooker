package com.los3molineros.snooker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class PlayerResponse(
    val ID: Int = 0,
    val Type: Int = 0,
    val FirstName: String = "",
    val MiddleName: String = "",
    val LastName: String = "",
    val TeamName: String = "",
    val TeamNumber: Int = 0,
    val TeamSeason: Int = 0,
    val ShortName: String = "",
    val Nationality: String = "",
    val Sex: String = "",
    val BioPage: String = "",
    val Born: String = "",
    val Twitter: String = "",
    val SurnameFirst: Boolean = false,
    val License: String  = "",
    val Club: String = "",
    val URL: String = "",
    val Photo: String = "",
    val PhotoSource: String = "",
    val FirstSeasonAsPro: Int = 0,
    val LastSeasonsAsPro: Int = 0,
    val Info: String = "",
)

@Entity
data class PlayerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "nationality")
    var nationality: String = "",
    @ColumnInfo(name = "sex")
    var sex: String = "",
    @ColumnInfo(name = "bioPage")
    var bioPage: String = "",
    @ColumnInfo(name = "born")
    var born: String = "",
    @ColumnInfo(name = "photo")
    var photo: String = "",
    @ColumnInfo(name = "firstSeasonAsPro")
    var firstSeasonAsPro: Int = 0,
)


fun PlayerResponse.toPlayerEntity() = PlayerEntity(
    this.ID,
    if (this.SurnameFirst) {
        this.LastName + " " + this.FirstName
    } else {
        this.FirstName + " " + this.LastName
    },
    this.Nationality,
    this.Sex,
    this.BioPage,
    this.Born,
    this.Photo,
    this.FirstSeasonAsPro,
)

fun List<PlayerResponse>.toPlayerEntityList(): List<PlayerEntity> {
    val playerEntityList = mutableListOf<PlayerEntity>()

    for (playerResponse in this) {
        playerEntityList.add(playerResponse.toPlayerEntity())
    }

    return playerEntityList
}
package com.los3molineros.snooker.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MatchResponse (
    val ID: Int = 0,
    val EventID: Int = 0,
    val Round: Int = 0,
    val Number: Int = 0,
    val Player1ID: Int = 0,
    val Score1: Int = 0,
    val Walkover1: Boolean = false,
    val Player2ID: Int = 0,
    val Score2: Int = 0,
    val Walkover2: Boolean = false,
    val WinnerID: Int = 0,
    val Unfinished: Boolean = false,
    val OnBreak: Boolean = false,
    val WorldSnookerID: Int = 0,
    val LiveUrl: String = "",
    val DetailsUrl: String = "",
    val PointsDropped: Boolean = false,
    val ShowCommonNote: Boolean = false,
    val Estimated: Boolean = false,
    val Type: Int = 0,
    val TableNo: Int = 0,
    val VideoURL: String = "",
    val InitDate: String = "",
    val ModDate: String = "",
    val StartDate: String = "",
    val EndDate: String = "",
    val ScheduledDate: String = "",
    val FrameScores: String = "",
    val Sessions: String = "",
    val Note: String = "",
    val ExtendedNote: String = ""
        )

@Parcelize
data class MatchUI (
    val ID: Int = 0,
    val EventID: Int = 0,
    val Round: Int = 0,
    val RoundName: String = "",
    val Number: Int = 0,
    val Player1ID: Int = 0,
    val Player1Name: String = "",
    val Player1Image: String = "",
    val Score1: Int = 0,
    val Walkover1: Boolean = false,
    val Player2ID: Int = 0,
    val Player2Name: String = "",
    val Player2Image: String = "",
    val Score2: Int = 0,
    val Walkover2: Boolean = false,
    val WinnerID: Int = 0,
    val Unfinished: Boolean = false,
    val OnBreak: Boolean = false,
    val WorldSnookerID: Int = 0,
    val LiveUrl: String = "",
    val DetailsUrl: String = "",
    val PointsDropped: Boolean = false,
    val ShowCommonNote: Boolean = false,
    val Estimated: Boolean = false,
    val Type: Int = 0,
    val TableNo: Int = 0,
    val VideoURL: String = "",
    val InitDate: String = "",
    val ModDate: String = "",
    val StartDate: String = "",
    val EndDate: String = "",
    val ScheduledDate: String = "",
    val FrameScores: String = "",
    val Sessions: String = "",
    val Note: String = "",
    val ExtendedNote: String = ""
): Parcelable


fun MatchResponse.toMatchUI(roundName: String, player1Name: String, player1Image: String, player2Name: String, player2Image: String) = MatchUI(
    this.ID,
    this.EventID,
    this.Round,
    roundName,
    this.Number,
    this.Player1ID,
    player1Name,
    player1Image,
    this.Score1,
    this.Walkover1,
    this.Player2ID,
    player2Name,
    player2Image,
    this.Score2,
    this.Walkover2,
    this.WinnerID,
    this.Unfinished,
    this.OnBreak,
    this.WorldSnookerID,
    this.LiveUrl,
    this.DetailsUrl,
    this.PointsDropped,
    this.ShowCommonNote,
    this.Estimated,
    this.Type,
    this.TableNo,
    this.VideoURL,
    this.InitDate,
    this.ModDate,
    this.StartDate,
    this.EndDate,
    this.ScheduledDate,
    this.FrameScores,
    this.Sessions,
    this.Note,
    this.ExtendedNote,
)


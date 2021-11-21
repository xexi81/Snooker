package com.los3molineros.snooker.domain.main

import com.los3molineros.snooker.common.CommonFunctions
import com.los3molineros.snooker.common.CommonFunctions.debugLog
import com.los3molineros.snooker.data.local.LocalPlayerDataSource
import com.los3molineros.snooker.data.model.*
import com.los3molineros.snooker.data.remote.RemoteEventsDataSource
import com.los3molineros.snooker.data.remote.RemotePlayerDataSource

class TournamentRepositoryImpl(
    private val remoteEventsDataSource: RemoteEventsDataSource,
    private val remotePlayerDataSource: RemotePlayerDataSource,
    private val localPlayerDataSource: LocalPlayerDataSource
) : TournamentRepository {

    // Get detail Tournament
    override suspend fun getTournament(id: Int): Tournament {

        var playerName: String = ""
        var playerImage: String = ""
        var totalPrice: Int = 0
        var winnerPrice: Int = 0

        // Internet validation
        if (!CommonFunctions.isNetworkAvailable()) {
            throw InternalError("No connexion to internet")
        }

        // EventResponse
        val event: SeasonEventResponse = remoteEventsDataSource.getEvent(id)[0]
        if (event.ID == 0) {
            throw InternalError("No data")
        }

        // Rounds
        val roundEventList = remoteEventsDataSource.getRoundsEvent(id)
        if (roundEventList.isEmpty()) {
            throw InternalError("No data")
        }

        for (item in roundEventList) {
            if (item.NumMatches == 0) {
                winnerPrice = item.Money
                totalPrice += item.Money
            } else totalPrice += item.Money * item.NumMatches
        }

        // Players
        if (event.DefendingChampion != 0) {
            var playerEntity = localPlayerDataSource.getPlayer(event.DefendingChampion)
            if (playerEntity == null) {
                playerEntity =
                    remotePlayerDataSource.getPlayerById(event.DefendingChampion)[0].toPlayerEntity()
                localPlayerDataSource.savePlayer(playerEntity)
                playerName = playerEntity.name
                playerImage = playerEntity.photo
            } else {
                playerName = playerEntity.name
                playerImage = playerEntity.photo
            }
        }

        return Tournament(
            event.ID,
            "${event.Sponsor} ${event.Name}",
            event.City,
            event.Venue,
            event.StartDate,
            event.EndDate,
            event.Type,
            playerName,
            playerImage,
            totalPrice,
            winnerPrice
        )
    }

    // Get all matches from a Tournament
    override suspend fun getMatches(id: Int): List<MatchUI> {
        val matchUIList: MutableList<MatchUI> = mutableListOf()

        // Internet validation
        if (!CommonFunctions.isNetworkAvailable()) {
            throw InternalError("No connexion to internet")
        }

        // MatchesResponse
        val matchList: List<MatchResponse> = remoteEventsDataSource.getMatchesEvent(id)
        if (matchList.isEmpty()) {
            throw InternalError("No data")
        }

        // Round Information
        val roundList = remoteEventsDataSource.getRoundsEvent(id)
        if (roundList.isEmpty()) {
            throw InternalError("No data")
        }

        // Loop and return all player and event names
        for (match in matchList) {
            var player1: PlayerEntity? = PlayerEntity()
            var player2: PlayerEntity? = PlayerEntity()

            // GetPlayers
            if (match.Player1ID != 376) {
                player1 = getPlayerFromId(match.Player1ID)
            }

            if (match.Player2ID != 376) {
                player2 = getPlayerFromId(match.Player2ID)
            }

            // GetRounds
            var roundName = ""
            val roundEventList = roundList.filter { it.Round == match.Round }

            // RoundName
            if (roundEventList.isNotEmpty()) {
                roundName = roundEventList[0].RoundName
            }

            matchUIList.add(match.toMatchUI(roundName, player1?.name ?: "", player1?.photo ?: "", player2?.name ?: "", player2?.photo ?: ""))
        }

        return matchUIList
    }


    // Get player data
    private suspend fun getPlayerFromId(playerID: Int): PlayerEntity? {
        if (playerID == 0 || playerID == 376) {
            return null
        }

        var playerEntity = localPlayerDataSource.getPlayer(playerID)
        if (playerEntity == null) {
            playerEntity = remotePlayerDataSource.getPlayerById(playerID)[0].toPlayerEntity()
            localPlayerDataSource.savePlayer(playerEntity)
            return playerEntity
        } else {
            return playerEntity
        }
    }


}
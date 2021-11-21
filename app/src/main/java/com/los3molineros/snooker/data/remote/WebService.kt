package com.los3molineros.snooker.data.remote

import com.google.gson.GsonBuilder
import com.los3molineros.snooker.common.AppConstants
import com.los3molineros.snooker.common.UnsafeOkHttpClient.Companion.getUnsafeOkHttpClient
import com.los3molineros.snooker.data.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET(".")
    suspend fun getPlayers(@Query("t") t: Int = 10, @Query("st") st: String = "b", @Query("s") s: Int): List<PlayerResponse>

    @GET(".")
    suspend fun getPlayerById(@Query("p") p: Int): List<PlayerResponse>

    @GET(".")
    suspend fun getRanking(@Query("rt") rt: String, @Query("s") s: Int): List<RankingResponse>

    @GET(".")
    suspend fun getSeasonEvents(@Query("t") t: Int = 5, @Query("s") s: Int): List<SeasonEventResponse>

    @GET(".")
    suspend fun getEvent(@Query("e") e: Int ): List<SeasonEventResponse>

    @GET(".")
    suspend fun getRoundsEvent(@Query("t") t: Int = 12, @Query("e") e: Int): List<RoundEvent>

    @GET(".")
    suspend fun getMatchesEvent(@Query("t") t: Int = 6, @Query("e") e: Int): List<MatchResponse>
}

object RetrofitClient {
    val webservice: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(getUnsafeOkHttpClient().build())
            .build()
            .create(WebService::class.java)
    }
}
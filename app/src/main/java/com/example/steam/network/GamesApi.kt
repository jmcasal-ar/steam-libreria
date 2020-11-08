package com.example.steam.network

import com.example.steam.data.GameResponse
import retrofit2.Call
import retrofit2.http.GET

interface GamesApi {
    @GET("new_games")
    fun getNewGames(): Call<List<GameResponse>>
}
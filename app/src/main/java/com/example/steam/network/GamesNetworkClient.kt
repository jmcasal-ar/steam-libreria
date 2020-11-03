package com.example.steam.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GamesNetworkClient {
    private const val BASE_URL = "https://demo6656703.mockable.io/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val gamesApi = retrofit.create(GamesApi::class.java)
}
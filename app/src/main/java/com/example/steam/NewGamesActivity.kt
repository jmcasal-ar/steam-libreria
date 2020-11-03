package com.example.steam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.steam.network.GamesNetworkClient
import retrofit2.Call
import retrofit2.Callback



import retrofit2.Response



class NewGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_games)

        retrieveNewGames()
    }

    private fun retrieveNewGames() {
        GamesNetworkClient.gamesApi.getNewGames().enqueue(object : Callback<List<GameResponse>> {
            override fun onResponse(
                call: Call<List<GameResponse>>,
                response: Response<List<GameResponse>>
            ) {
                response.body()
            }

            override fun onFailure(call: Call<List<GameResponse>>, t: Throwable) {
                Log.e("MainActivity", "Error al obtener juevos nuevos")
            }
        })
    }
}
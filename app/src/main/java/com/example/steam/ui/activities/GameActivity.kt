package com.example.steam.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.steam.data.Game
import com.example.steam.R

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //ejemplo de como ser parceable
        val game = intent.extras?.getParcelable<Game>("GAME")
        game?.name
    }

}
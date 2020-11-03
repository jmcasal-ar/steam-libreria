package com.example.steam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //ejemplo de como ser parceable
        val game = intent.extras?.getParcelable<Game>("GAME")
        game?.name
    }

}
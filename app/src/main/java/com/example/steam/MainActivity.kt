package com.example.steam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var  rvGames : RecyclerView
    private val adapter: GamesAdapter by lazy { GamesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        retrieveGames()
    }

    private fun retrieveGames() {
        val games = GamesProvider.getGames()
        adapter.updateGames(games)
    }

    private fun setupUI() {
        rvGames = findViewById(R.id.rvGames)
        rvGames.adapter = adapter
    }
}
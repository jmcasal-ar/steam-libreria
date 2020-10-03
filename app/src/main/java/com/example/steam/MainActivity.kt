package com.example.steam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import com.google.android.material.snackbar.Snackbar as Snackbar

//despues de la compa implementamos la interafaz
class MainActivity : AppCompatActivity(), GamesListener {

    private lateinit var  rvGames : RecyclerView
    private lateinit var coordinatorLayout: CoordinatorLayout
    private val adapter: GamesAdapter by lazy { GamesAdapter(this) }

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
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        rvGames = findViewById(R.id.rvGames)
        rvGames.adapter = adapter
    }

    override fun onGameClicked(game: Game) {
        Snackbar
            .make(coordinatorLayout, "work in progres", Snackbar.LENGTH_LONG)
            .show()
    }
}
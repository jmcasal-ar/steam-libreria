package com.example.steam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.view.*
import com.google.android.material.snackbar.Snackbar as Snackbar

//despues de la compa implementamos la interafaz
class MainActivity : AppCompatActivity(), GamesListener {

    private lateinit var  rvGames : RecyclerView
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var fabAdd: FloatingActionButton
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
        fabAdd = findViewById(R.id.floatingActionButton)
        fabAdd.setOnClickListener{launchAddGameActivity()}
        rvGames = findViewById(R.id.rvGames)
        rvGames.adapter = adapter
    }

    private fun launchAddGameActivity() {
        startActivity(
            Intent(this, AddGameActivity::class.java)
        )
    }

    override fun onGameClicked(game: Game) {
        /*
        Snackbar
            .make(coordinatorLayout, "work in progres", Snackbar.LENGTH_LONG)
            .show()

         */

        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(game.name)
            .setMessage("Este juego posee ${game.perCentDiscount} de descuento")
            .setPositiveButton("DETALLE", {_, _ ->
                Snackbar.make(
                    coordinatorLayout, "In progress", Snackbar.LENGTH_LONG
                )
            })
            .setNeutralButton("MODIFICAR", { _, _ ->

            })
            .setNegativeButton("ELIMINAR", { _, _ ->

            })
            .setCancelable(false)
            .show()
    }

}
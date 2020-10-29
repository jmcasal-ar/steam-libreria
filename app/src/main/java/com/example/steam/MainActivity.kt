package com.example.steam

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.inflate
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.steam.db.GamesRpository
import com.example.steam.preferences.PreferenceActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.view.*
import com.google.android.material.snackbar.Snackbar as Snackbar

//despues de la compa implementamos la interafaz
class MainActivity : AppCompatActivity(), GamesListener {

    private lateinit var  rvGames : RecyclerView
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var fabAdd: FloatingActionButton
    private val adapter: GamesAdapter by lazy { GamesAdapter(this) }
    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()

    }

    override fun onResume() {
        super.onResume()
        //esto es para mostar u ocultar el boton fab a través de la visibility
        handleFabAddVisibility()
        retrieveGames()
    }

    private fun retrieveGames() {
        val games = GamesRpository(this@MainActivity.applicationContext).getGames()
        //val games = GamesProvider.getGames()
        adapter.updateGames(games)
    }

    private fun setupUI() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        fabAdd = findViewById(R.id.floatingActionButton)
        fabAdd.setOnClickListener{launchAddGameActivity()}




        rvGames = findViewById(R.id.rvGames)
        rvGames.adapter = adapter
    }

    private fun handleFabAddVisibility() {
        val showIdShowFabAdd = preferences.getBoolean("switchShowAddButton", true)
        if (showIdShowFabAdd){
            fabAdd.show()
        } else  {
            fabAdd.hide()
        }
    }

    private fun launchAddGameActivity() {
        startActivity(
            Intent(this, AddGameActivity::class.java)
        )
    }

    override fun onGameClicked(game: Game) {
        //ejemplo de como cargar un juego
        val intent = Intent()
        //como hacemos para pasar un tipo game?
        intent.putExtra("Game", game)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemSettings) {
            launchSettings()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun launchSettings() {
        startActivity(
            Intent(this, PreferenceActivity::class.java)
        )
    }
}
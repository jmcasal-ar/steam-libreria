package com.example.steam.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.steam.data.Game
import com.example.steam.R
import com.example.steam.db.GamesDao
import com.example.steam.preferences.PreferenceActivity
import com.example.steam.ui.adapter.GamesAdapter
import com.example.steam.ui.adapter.GamesListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

//despues de la compa implementamos la interafaz
class MainActivity : AppCompatActivity(),
    GamesListener {

    private lateinit var  rvGames : RecyclerView
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var fabAdd: FloatingActionButton
    private val adapter: GamesAdapter by lazy {
        GamesAdapter(
            this
        )
    }
    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val compositeDisposable = CompositeDisposable()

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
        /*
        val games = GamesRpository(this@MainActivity.applicationContext).getGames()
        //val games = GamesProvider.getGames()
        adapter.updateGames(games)
        */
        GamesDao(this@MainActivity.applicationContext)
            .getGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Game>>{
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(games: List<Game>) {
                    adapter.updateGames(games)
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity", "Error al obtener juegos", e)
                }

            })
    }

    private fun setupUI() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        fabAdd = findViewById(R.id.floatingActionButton)
        fabAdd.setOnClickListener{launchAddGameActivity()}




        rvGames = findViewById(R.id.rvGames)
        rvGames.adapter = adapter
    }

    private fun handleFabAddVisibility() {
        Single.fromCallable { preferences.getBoolean("switchShowAddButton", true) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Boolean>{
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(shouldShowFabAdd: Boolean) {
                    if (shouldShowFabAdd) {
                        fabAdd.show()
                    } else {
                        fabAdd.hide()
                    }
                }

                override fun onError(e: Throwable) {
                    Log.i("MainActivity","Error al obtener las preferencias - shouldShowFabAdd", e)
                }
            })
        /*val showIdShowFabAdd = preferences.getBoolean("switchShowAddButton", true)
        if (showIdShowFabAdd){
            fabAdd.show()
        } else  {
            fabAdd.hide()
        }*/
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

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
package com.example.steam.db

import android.content.Context
import com.example.steam.Game
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao

class GamesRpository(context: Context) {

    //creamos una instancia dao que tiene su int
    private var dao: Dao<Game, Int>

    init {
        //el metodo init nos permite ejecutar un codigo cuando se instancia una clase
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(Game::class.java)
    }

    //metodo para crear juego
    fun addGame(game:Game) = dao.create(game)

    fun deleteGame(game: Game) = dao.delete(game)

    fun updateGame(game: Game) = dao.update(game)

    fun getGames() = dao.queryForAll()

    fun getGame(gameId: Int) = dao.queryForId(gameId)



}
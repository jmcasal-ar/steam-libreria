package com.example.steam.db

import android.content.Context
import com.example.steam.data.Game
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GamesDao(context: Context) {

    //creamos una instancia dao que tiene su int
    private var dao: Dao<Game, Int>

    init {
        //el metodo init nos permite ejecutar un codigo cuando se instancia una clase
        val helper = OpenHelperManager.getHelper(context, DBHelper::class.java)
        dao = helper.getDao(Game::class.java)
    }

    //metodo para crear juego
    fun addGame(game: Game): Completable {
        return Completable
            .fromCallable { dao.create(game) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteGame(game: Game) = dao.delete(game)

    fun updateGame(game: Game) = dao.update(game)

    //asi hacemos un enumerable
    fun getGames(): Single<List<Game>> {

        return Single.
        fromCallable{ dao.queryForAll() } //crea un obsevable (Single) a ima función que se va a lianar, es decir, la encapsula
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        /*return  Single.just(dao.queryForAll()) //crea instancia del tipo obsevable (Single) a partir de la llamada a dao.queryfor
            .subscribeOn(Scheduler.io())
            .observeOn(AndroidSchedulers.mainThread())
            */

    }

    fun getGame(gameId: Int) = dao.queryForId(gameId)



}
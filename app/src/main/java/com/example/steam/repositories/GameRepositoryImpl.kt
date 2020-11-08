package com.example.steam.repositories

import com.example.steam.data.Game
import com.example.steam.db.GamesDao
import io.reactivex.disposables.CompositeDisposable

class GameRepositoryImpl(
    private val gamesDao: GamesDao,
    private val compositeDisposable: CompositeDisposable
) : GameRepository {
    override fun addGame(game: Game, success: () -> Unit, error: () -> Unit) {
        compositeDisposable.add(
            gamesDao
                .addGame(game)
                .subscribe({
                    success()
                }, {
                    error()
                })
        )

    }
}
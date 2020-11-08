package com.example.steam.presenters

import com.example.steam.data.Game
import com.example.steam.repositories.GameRepository
import com.example.steam.ui.activities.interfaces.AddGameView

class AddGamePresenterImpl(
    private val view: AddGameView,
    private val repository: GameRepository
): AddGamePresenter {
    override fun doAddGame(game: Game) {
        view.showLoading()
        repository
            .addGame(game, {
                // Success
                view.hideLoading()
                view.goBack()
            }, {
                //Error
                view.hideLoading()
                view.showErrorMessage()
            })
    }
}
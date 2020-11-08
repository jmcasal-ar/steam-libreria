package com.example.steam.presenters

import com.example.steam.data.Game

interface AddGamePresenter {
    fun doAddGame(game: Game)
}
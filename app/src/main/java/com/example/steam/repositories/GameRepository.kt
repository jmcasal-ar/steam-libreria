package com.example.steam.repositories

import com.example.steam.data.Game

interface GameRepository {

    fun addGame(game: Game, success: () -> Unit, error: () -> Unit)
}
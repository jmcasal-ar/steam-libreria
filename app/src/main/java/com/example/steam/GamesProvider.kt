package com.example.steam

import com.example.steam.data.Game

object GamesProvider {

    fun getGames() = listOf<Game>(
        Game(
            R.drawable.ic_launcher_foreground, //TODO agregar una imagen verdadera
            "BIOSHOP TRIPLE PACK",
            "-65%",
            "69,97",
            "10,48",
            "Bioshop is a FPS game" //Todo agregar una imagen verdadera
        ),
        Game(
            R.drawable.ic_launcher_foreground,
            "Surival Evolved",
            "-17%",
            "29,99",
            "24,89",
            "Bioshop is a FPS game" //Todo agregar una imagen verdadera
        ),
        Game(
            R.drawable.ic_launcher_foreground,
            "Counter Strike: Global Offensive",
            "-50%",
            "14,99",
            "7,49",
            "Bioshop is a FPS game" //Todo agregar una imagen verdadera
        )
    )
}
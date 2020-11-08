package com.example.steam.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.steam.data.Game
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

class DBHelper(context: Context) : OrmLiteSqliteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    //tenemos que si o si implementar dos elementos ya que no es una clase abstracta
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        //se crea una sola vez la tabla
        TableUtils.createTableIfNotExists(connectionSource, Game::class.java)
    }

    override fun onUpgrade(
        //cuando se actualiza la version
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion == 1 && newVersion ==2){
            //crear tablas amigos


            onCreate(database, connectionSource)
        }
    }

}
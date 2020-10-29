package com.example.steam

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize


@DatabaseTable(tableName = "Game")


//Estas son las propiedades de cada juego
//Nos va a servir para que dentro del adapter tengamos to do definido
//parcelize se utiliza para parsear.
@Parcelize
class Game (
    @DatabaseField(columnName = "ImageResource")
    val resImage: Int,
    @DatabaseField
    val name: String,
    @DatabaseField
    val perCentDiscount: String,
    @DatabaseField
    val discountPrice: String,
    @DatabaseField
    val price: String,
    @DatabaseField
    val description: String,

    //importante hacer id nulleable
    @DatabaseField(id = true)
    val id: Int? = null
): Parcelable {
    constructor() : this(0, "","","","","")
}

//Parcelable se utiliza para poder castear
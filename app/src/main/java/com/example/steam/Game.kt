package com.example.steam

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//Estas son las propiedades de cada juego
//Nos va a servir para que dentro del adapter tengamos to do definido
//parcelize se utiliza para parsear.
@Parcelize
class Game (
    val resImage: Int,
    val name: String,
    val perCentDiscount: String,
    val discountPrice: String,
    val price: String,
    val description: String
): Parcelable

//Parcelable se utiliza para poder castear
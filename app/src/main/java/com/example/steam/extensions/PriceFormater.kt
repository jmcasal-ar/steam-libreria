package com.example.steam.extensions

//vamos a agregar este metodo dentro de la clase String
fun String.toPriceFormat(): String{
    return "$${this.toDouble()}"
}

fun String.toPercentDiscount(): String {
    return "-$this%"
}
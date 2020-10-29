package com.example.steam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.steam.extensions.toPercentDiscount
import com.example.steam.extensions.toPriceFormat

//casteo el listener con GamesListener
//cuando llamemos a esta clase hay que darle una implementación de una interfaz
class GamesAdapter(val listener: GamesListener): RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    private var games: List<Game> = emptyList()

    class GamesViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val imgGame = itemView.findViewById<ImageView>(R.id.imgImage)
        //dos formas de hacer los mismo
        val txtName: TextView = itemView.findViewById((R.id.txtName))
        val txtPerCent: TextView = itemView.findViewById((R.id.txtPerCent))
        val txtDiscount: TextView = itemView.findViewById((R.id.txtDiscount))
        val txtPrice: TextView = itemView.findViewById((R.id.txtPrice))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GamesAdapter.GamesViewHolder {
        val itemView = LayoutInflater.from(parent.context).
        inflate(R.layout.item_game,
        parent, false)
        return GamesViewHolder(itemView)
    }

    override fun getItemCount() = games.size

    //provee el viewholder con la posicion y con eso el item de cada uno de los viedeos juegos
    override fun onBindViewHolder(holder: GamesAdapter.GamesViewHolder, position: Int) {
        holder.apply {
            //recorremos la lista según la posición. Apply nos permite recorrer directamente la propiedad
            imgGame.setImageResource(games[position].resImage)
            txtName.text = games[position].name
            txtPerCent.text = games[position].perCentDiscount.toPercentDiscount()
            txtDiscount.text = games[position].discountPrice.toPriceFormat()
            txtPrice.text = games[position].price.toPriceFormat()

            //tratamos de capturar el click
            itemView.setOnClickListener {
                listener.onGameClicked(games[position])
            }
        }
    }

    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }
}
//Creamos una interfaz para hacer solid
interface GamesListener{
    fun onGameClicked(game: Game)
}
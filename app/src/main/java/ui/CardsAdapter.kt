package ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mtg_api.R
import model.Card

class CardsAdapter : RecyclerView.Adapter<CardViewHolder>() {

    var items: MutableList<Card> = ArrayList<Card>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.name.text = items[position].name
       // holder.img.image = items[position].domains[0]
    }
    fun Update(lista: MutableList<Card>){
        items = lista
        this.notifyDataSetChanged()
    }

}
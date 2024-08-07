package ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mtg_api.R
import model.Card

class CardsAdapter : RecyclerView.Adapter<CardViewHolder>() {

    var onItemClick : ((Card) -> Unit)? = null
    private var items: MutableList<Card> = ArrayList<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("Size", "Size: " + items.size)
        return items.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = items[position]

        card.image_uris?.let { holder.bind(it.png) }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(card)
            val id = items[position].name
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("name", id)
            holder.itemView.context.startActivity(intent)
        }

    }
    fun update(lista: MutableList<Card>){
        items = lista
        this.notifyDataSetChanged()
    }
}


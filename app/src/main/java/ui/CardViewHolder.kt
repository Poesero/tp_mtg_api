package ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tp_mtg_api.R

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val img: ImageView = itemView.findViewById(R.id.card_img)
    fun bind(imageUrl: String) {
        Glide.with(itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.default_img)
            .error(R.drawable.default_img)
            .into(img)
    }
}
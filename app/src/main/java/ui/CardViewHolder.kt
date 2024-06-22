package ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mtg_api.R

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val img: ImageView = itemView.findViewById(R.id.card_img)
    /*recordar: se setea img usando la libreria Glide*/
}
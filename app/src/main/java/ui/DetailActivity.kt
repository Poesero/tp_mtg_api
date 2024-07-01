package ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tp_mtg_api.R

class DetailActivity : AppCompatActivity() {
    private lateinit var cardImg: ImageView
    private lateinit var cardName: TextView
    private lateinit var oracleTxt: TextView
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.card_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cardImg = findViewById(R.id.card_img)
        cardName = findViewById(R.id.card_name)
        oracleTxt = findViewById(R.id.oracle_txt)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]


        val name = intent.getStringExtra("name")!!
        Log.d("DetailActivity", "Received query: $name")

        viewModel.init(name)
        viewModel.card.observe(this,Observer{ card ->
            card?.let {
                cardName.text = it.name
                oracleTxt.text = it.oracle_text
                Glide.with(this)
                    .load(it.image_uris?.png)
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.default_img)
                    .into(cardImg)
            }
        })

    }


}
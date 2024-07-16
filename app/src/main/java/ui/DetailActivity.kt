package ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tp_mtg_api.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var back: ImageButton
    private lateinit var favBtn: ToggleButton
    private lateinit var cardImg: ImageView
    private lateinit var cardName: TextView
    private lateinit var oracleTxt: TextView
    private lateinit var typeTxt: TextView
    private lateinit var viewModel: DetailViewModel
    private lateinit var pb: ProgressBar
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_card_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.card_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pb = findViewById(R.id.progressbar)
        back = findViewById(R.id.step_back)
        favBtn = findViewById(R.id.fav_btn)
        cardImg = findViewById(R.id.card_img)
        cardName = findViewById(R.id.card_name)
        oracleTxt = findViewById(R.id.oracle_txt)
        typeTxt = findViewById(R.id.type_line)

        userId = FirebaseAuth.getInstance().currentUser?.email

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val isRandom = intent.getBooleanExtra("random", false)
        val name = intent.getStringExtra("name") ?: ""
        Log.d("DetailActivity", "Received query: $name")

        pb.visibility = ProgressBar.VISIBLE
        if (isRandom) {
            viewModel.fetchRandomCard()
        } else
            viewModel.init(name)

        viewModel.card.observe(this, Observer { card ->
            card?.let {
                pb.visibility = ProgressBar.INVISIBLE
                cardName.text = it.name
                oracleTxt.text = it.oracle_text
                typeTxt.text = it.type_line
                Glide.with(this)
                    .load(it.image_uris?.png)
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.default_img)
                    .into(cardImg)
            }
        })

    }


}
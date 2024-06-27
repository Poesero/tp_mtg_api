package ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp_mtg_api.R

class DetailActivity : AppCompatActivity() {
    private lateinit var cardImg: ImageView
    private lateinit var cardName: TextView
    private lateinit var oracleTxt: TextView
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

    }
}
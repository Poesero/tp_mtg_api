package ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mtg_api.R

class FavActivity : AppCompatActivity() {
    private lateinit var imageButton: ImageButton
    private lateinit var adapter: CardsAdapter
    private lateinit var viewModel: FavViewmodel
    private lateinit var cvCards: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.favorites)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cvCards = findViewById(R.id.cvCards)
        cvCards.layoutManager = LinearLayoutManager(this)
        adapter = CardsAdapter()
        cvCards.adapter = adapter

        viewModel = ViewModelProvider(this)[FavViewmodel::class.java]
        viewModel.cards.observe(this) {
            adapter.update(it)
        }

        imageButton = findViewById(R.id.step_back)
        imageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewModel.init(this)

        adapter.onItemClick = { card ->
            val intent = Intent(this@FavActivity, DetailActivity::class.java).apply {
                putExtra("name", card.name)
            }
            startActivity(intent)
            finish()
        }
    }
}

package ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mtg_api.R


class SearchActivity : AppCompatActivity() {
    private lateinit var cvCards: RecyclerView
    private lateinit var adapter: CardsAdapter
    private lateinit var viewModel: SearchViewModel
    private lateinit var imageButton: ImageButton
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.searcher)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cvCards = findViewById(R.id.cvCards)
        cvCards.layoutManager = LinearLayoutManager(this)
        adapter = CardsAdapter()
        cvCards.adapter = adapter

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.cards.observe(this) {
            adapter.update(it)
        }

        searchView = findViewById(R.id.searchView)
        searchView.isIconified = false
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.init(it)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        imageButton = findViewById(R.id.step_back)
        imageButton.setOnClickListener {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val name = intent.getStringExtra("name")!!
        Log.d("SearchActivity", "Received query: $name")

        viewModel.init(name)

        adapter.onItemClick = { card ->
            val intent = Intent(this@SearchActivity, DetailActivity::class.java).apply {
                putExtra("name", card.name)
            }
            startActivity(intent)
            finish()
        }

    }

}

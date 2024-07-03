package ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
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
    lateinit var pb : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.searcher)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pb = findViewById(R.id.progressbar)
        cvCards = findViewById(R.id.cvCards)
        cvCards.layoutManager = LinearLayoutManager(this)
        adapter = CardsAdapter()
        cvCards.adapter = adapter

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.cards.observe(this) {
            pb.visibility = View.INVISIBLE
            adapter.update(it)

            Toast.makeText(this@SearchActivity, "Cards found: ${it.size}", Toast.LENGTH_LONG).show()
        }

        searchView = findViewById(R.id.searchView)
        searchView.isIconified = false
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    pb.visibility= View.VISIBLE
                    viewModel.init("",this@SearchActivity)
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
        val color = intent.getStringExtra("color")
        Log.d("SearchActivity", "Received query: $color")
        Log.d("SearchActivity", "Received query: $name")

        pb.visibility= View.VISIBLE

        if (!color.isNullOrEmpty()) {
                viewModel.init(name,color)
            }else{
                viewModel.init(name,this)
            }


        adapter.onItemClick = { card ->
            val intent = Intent(this@SearchActivity, DetailActivity::class.java).apply {
                putExtra("name", card.name)
            }
            startActivity(intent)
            finish()
        }

    }

}

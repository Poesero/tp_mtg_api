package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mtg_api.R


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var searchBtn: ImageButton
    private lateinit var searchView: SearchView

    private lateinit var white: ToggleButton
    private lateinit var blue: ToggleButton
    private lateinit var black: ToggleButton
    private lateinit var red: ToggleButton
    private lateinit var green: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.searchView)
        searchView.isIconified = false
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val intent = Intent(this@MainActivity,SearchActivity::class.java)
                    intent.putExtra("name",it)
                    startActivity(intent)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })


        white= findViewById (R.id.toggleBtn_W)
        blue= findViewById (R.id.toggleBtn_U)
        black= findViewById (R.id.toggleBtn_B)
        red= findViewById (R.id.toggleBtn_R)
        green = findViewById (R.id.toggleBtn_G)

        searchBtn = findViewById(R.id.search_btn)
        bindViewModel()
        searchBtn.setOnClickListener{
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart(this)
    }

    private fun bindViewModel(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.cards.observe(this){
        }

    }
}
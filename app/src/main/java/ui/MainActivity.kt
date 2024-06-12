package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mtg_api.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private val searchBtn: ImageButton = findViewById(R.id.search_btn)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViewModel()

        searchBtn.setOnClickListener{
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    private fun bindViewModel(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.cards.observe(this){
        }

    }
}
package ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mtg_api.R


class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        bindViewModel()
    }

    override fun onStart(){
        super.onStart()
        viewModel.onStart()
    }

    private fun bindViewModel(){
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
    }
}

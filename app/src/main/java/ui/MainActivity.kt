package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mtg_api.R
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var favBtn: ImageButton
    private lateinit var randBtn: ImageButton
    private lateinit var searchView: SearchView
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var white: ToggleButton
    private lateinit var blue: ToggleButton
    private lateinit var black: ToggleButton
    private lateinit var red: ToggleButton
    private lateinit var green: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var color = ""

        searchView = findViewById(R.id.searchView)
        searchView.isIconified = false
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val intent = Intent(this@MainActivity,SearchActivity::class.java)
                    intent.putExtra("name",it)
                    if (color.isNotEmpty()){
                        intent.putExtra("color",color)
                    }
                    startActivity(intent)
                    finish()
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

        favBtn = findViewById(R.id.fav_btn)
        randBtn = findViewById(R.id.random_btn)

        firebaseAuth = FirebaseAuth.getInstance()

        bindViewModel()

        favBtn.setOnClickListener{
            val intent = Intent(this,FavActivity::class.java)
            startActivity(intent)
        }
        randBtn.setOnClickListener{
            val intent = Intent(this,DetailActivity::class.java).apply {
            putExtra("random",true)
            }
            startActivity(intent)
        }

        white.setOnCheckedChangeListener{ _, isChecked->
            if (isChecked) {
                if (!color.contains("W")) {
                    color += "W"
                }
            } else {
                color = color.replace("W", "")
            }
        }

        blue.setOnCheckedChangeListener{ _, isChecked->
            if (isChecked) {
                if (!color.contains("U")) {
                    color += "U"
                }
            } else {
                color = color.replace("U", "")
            }
        }

        black.setOnCheckedChangeListener{ _, isChecked->
            if (isChecked) {
                if (!color.contains("B")) {
                    color += "B"
                }
            } else {
                color = color.replace("B", "")
            }
        }

        red.setOnCheckedChangeListener{ _, isChecked->
            if (isChecked) {
                if (!color.contains("R")) {
                    color += "R"
                }
            } else {
                color = color.replace("R", "")
            }
        }

        green.setOnCheckedChangeListener{ _, isChecked->
            if (isChecked) {
                if (!color.contains("G")) {
                    color += "G"
                }
            } else {
                color = color.replace("G", "")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart("",this)
    }

    private fun checkUser(){
        val  firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
    private fun bindViewModel(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.cards.observe(this){
        }

    }
}
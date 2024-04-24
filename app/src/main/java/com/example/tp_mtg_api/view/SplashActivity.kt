package com.example.tp_mtg_api.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tp_mtg_api.R
import com.example.tp_mtg_api.viewModel.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val loginBtn : Button =findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener{
            showLoginBox()
        }
/*
        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }, 9000)

*/
    }
    private fun showLoginBox(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_login)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnContinue : Button = dialog.findViewById(R.id.btn_continue)
        val btnClose : Button = dialog.findViewById(R.id.btn_close)

        btnContinue.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()

    }
}
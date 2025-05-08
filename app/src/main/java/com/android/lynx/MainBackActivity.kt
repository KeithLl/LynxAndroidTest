package com.android.lynx

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainBackActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initMain()
    }

    private fun initMain() {
        setContentView(R.layout.activity_main_back)
        val lynxView = findViewById<TextView>(R.id.tvMain)
        lynxView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}

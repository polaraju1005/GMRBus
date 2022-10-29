package com.example.gmrbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.gmrbus.logins.LoginActivity
import pl.droidsonroids.gif.GifImageView

class MainActivity : AppCompatActivity() {
    lateinit var gif: GifImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gif = findViewById(R.id.gifBus)

        supportActionBar?.hide()

        Handler().postDelayed(Runnable { display() }, 2000)
    }
    private fun display(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
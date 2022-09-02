package com.example.gmrbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class RegisterSplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_splash_screen)
        Handler().postDelayed(Runnable { display() }, 2000)
    }

    private fun display() {
        startActivity(Intent(this, AdminRegisterActivity::class.java))
        finish()
    }
}
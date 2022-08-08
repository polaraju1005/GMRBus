package com.example.gmrbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    lateinit var adminButton:Button
    lateinit var studentButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = "Login"

        adminButton = findViewById(R.id.btnAdmin)
        studentButton = findViewById(R.id.btnStudent)

        adminButton.setOnClickListener {
            startActivity(Intent(this,AdminLogin::class.java))
        }

        studentButton.setOnClickListener {
            startActivity(Intent(this,StudentLogin::class.java))
        }
    }
}
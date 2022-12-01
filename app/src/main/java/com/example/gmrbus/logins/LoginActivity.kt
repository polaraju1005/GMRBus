package com.example.gmrbus.logins

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.gmrbus.R

class LoginActivity : AppCompatActivity() {
    lateinit var adminButton:Button
    lateinit var studentButton:Button
    lateinit var parentButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        println("onCreate called")

        title = "Login"

        adminButton = findViewById(R.id.btnAdmin)
        studentButton = findViewById(R.id.btnStudent)
        parentButton = findViewById(R.id.btnParent)

        adminButton.setOnClickListener {
            startActivity(Intent(this, AdminLogin::class.java))
        }

        studentButton.setOnClickListener {
            startActivity(Intent(this, StudentLogin::class.java))
        }

        parentButton.setOnClickListener {
            startActivity(Intent(this,ParentLogin::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        print("onStart Called")
    }

    override fun onResume() {
        super.onResume()
        println("onResume Called")
    }

    override fun onPause() {
        super.onPause()
        println("onPause Called")
    }

    override fun onStop() {
        super.onStop()
        println("onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart called")
    }
}
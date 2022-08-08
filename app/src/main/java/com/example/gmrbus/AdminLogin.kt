package com.example.gmrbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class AdminLogin : AppCompatActivity() {
    lateinit var admHeader: TextView
    lateinit var icon: ImageView
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var forgot: TextView
    lateinit var loginBtn: Button
    lateinit var signUp: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        admHeader = findViewById(R.id.txtAdmHeader)
        icon = findViewById(R.id.imgProfile)
        email = findViewById(R.id.etLoginEmail)
        password = findViewById(R.id.etLoginPassword)
        forgot = findViewById(R.id.txtFgPassword)
        loginBtn = findViewById(R.id.btnLogin)
        signUp = findViewById(R.id.txtRegister)

        loginBtn.setOnClickListener {
            startActivity(Intent(this,AdminDashboard::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
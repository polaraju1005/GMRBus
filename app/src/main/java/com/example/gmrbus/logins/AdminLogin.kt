package com.example.gmrbus.logins

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.gmrbus.R
import com.example.gmrbus.dashboards.admin.AdminDashboard
import com.google.firebase.auth.FirebaseAuth

class AdminLogin : AppCompatActivity() {
    lateinit var admHeader: TextView
    private lateinit var icon: ImageView
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var forgot: TextView
    lateinit var loginBtn: Button
    lateinit var signUp: TextView
    lateinit var auth: FirebaseAuth
    lateinit var username: String
    lateinit var userPassword: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Admin login"

        admHeader = findViewById(R.id.txtAdmHeader)
        icon = findViewById(R.id.imgProfile)
        email = findViewById(R.id.etLoginEmail)
        password = findViewById(R.id.etLoginPassword)
        forgot = findViewById(R.id.txtFgPassword)
        loginBtn = findViewById(R.id.btnLogin)
        signUp = findViewById(R.id.txtRegister)
        auth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            username = email.text.toString().trim { it <= ' ' }
            userPassword = password.text.toString().trim { it <= ' ' }
            if (username.isEmpty()) {
                Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show()
            } else if (userPassword.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else if (userPassword.length < 8) {
                Toast.makeText(
                    this,
                    "Password length must be 8 characters long",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                login()
            }
        }

        signUp.setOnClickListener {
            startActivity(Intent(this, AdminRegisterActivity::class.java))
        }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(username, userPassword)
            .addOnCompleteListener(this@AdminLogin) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Logged in successfully !", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AdminDashboard::class.java))
                } else {
                    Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
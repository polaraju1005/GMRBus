package com.example.gmrbus.logins

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gmrbus.R
import com.example.gmrbus.dashboards.student.StudentDashboard
import com.google.firebase.auth.FirebaseAuth

class StudentLogin : AppCompatActivity() {
    lateinit var stuHeader: TextView
    lateinit var icon: ImageView
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
        setContentView(R.layout.activity_student_login)

        title = "Student login"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        stuHeader = findViewById(R.id.txtStuHeader)
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
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else if (userPassword.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else {
                login()
            }
        }

        signUp.setOnClickListener {
            startActivity(Intent(this, StudentRegisterActivity::class.java))
        }
    }

    private fun login(){
        auth.signInWithEmailAndPassword(username,userPassword).addOnCompleteListener(this@StudentLogin) { task->
            if (task.isSuccessful){
                Toast.makeText(this,"Logged in successfully !",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, StudentDashboard::class.java))
            }else{
                Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
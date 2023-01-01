package com.example.gmrbus.logins

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.example.gmrbus.R
import com.example.gmrbus.dashboards.admin.AdminDashboard
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth

class AdminLogin : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var forgot: TextView
    lateinit var loginBtn: Button
    lateinit var signUp: TextView
    lateinit var auth: FirebaseAuth
    lateinit var username: String
    lateinit var userPassword: String
    private lateinit var loadingBar:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        title = "Admin login"


        email = findViewById(R.id.inputEmail)
        password = findViewById(R.id.inputPassword)
        forgot = findViewById(R.id.forgotPassword)
        loginBtn = findViewById(R.id.btnLoginNew)
        signUp = findViewById(R.id.gotoRegister)
        auth = FirebaseAuth.getInstance()

        forgot.setOnClickListener {
            recoverPasswordDialog()
        }

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

    private fun recoverPasswordDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Recover Password")
        val linearLayout = LinearLayout(this)
        val emailet = EditText(this)

        emailet.setText("")
        emailet.setMinEms(16)
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        linearLayout.addView(emailet)
        linearLayout.setPadding(10, 10, 10, 10)
        builder.setView(linearLayout)

        builder.setPositiveButton(
            "Recover"
        ) { dialog, which ->
            val email = emailet.text.toString().trim { it <= ' ' }
            beginRecovery(email)
        }

        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.dismiss() }

        builder.create().show()
    }

    private fun beginRecovery(email: String) {
        loadingBar = ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                loadingBar.dismiss()
                if (task.isSuccessful) {
                    // if isSuccessful then done message will be shown
                    // and you can change the password
                    Toast.makeText(this@AdminLogin, "Done sent", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@AdminLogin, "Error Occurred", Toast.LENGTH_LONG).show()
                }
            }).addOnFailureListener(OnFailureListener {
                loadingBar.dismiss()
                Toast.makeText(this@AdminLogin, "Error Failed", Toast.LENGTH_LONG).show()
            })
    }

    private fun login() {
        auth.signInWithEmailAndPassword(username, userPassword)
            .addOnCompleteListener(this@AdminLogin) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Logged in successfully !", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AdminDashboard::class.java))
                    finish()
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
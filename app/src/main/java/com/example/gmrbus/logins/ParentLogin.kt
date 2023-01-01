package com.example.gmrbus.logins

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gmrbus.R
import com.example.gmrbus.dashboards.parent.ParentDashboard
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth

class ParentLogin : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    lateinit var forgot: TextView
    lateinit var login: Button
    lateinit var register: TextView
    lateinit var pauth: FirebaseAuth
    lateinit var parentUsername: String
    lateinit var parentPassword: String
    private lateinit var loadingBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_parent_login)
        supportActionBar?.hide()

        email = findViewById(R.id.inputEmail)
        password = findViewById(R.id.inputPassword)
        forgot = findViewById(R.id.forgotPassword)
        login = findViewById(R.id.btnParentLoginNew)
        register = findViewById(R.id.gotoRegister)

        pauth = FirebaseAuth.getInstance()

        forgot.setOnClickListener {
            recoverPasswordDialog()
        }

        login.setOnClickListener {
            parentUsername = email.text.toString().trim { it <= ' ' }
            parentPassword = password.text.toString().trim { it <= ' ' }
            if (parentUsername.isEmpty()) {
                Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show()
            } else if (parentPassword.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else if (parentPassword.length < 8) {
                Toast.makeText(
                    this,
                    "Password length must be 8 characters long",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getLogin()
            }
        }

        register.setOnClickListener {
            startActivity(Intent(this@ParentLogin, ParentRegistration::class.java))
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

        pauth.sendPasswordResetEmail(email)
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                loadingBar.dismiss()
                if (task.isSuccessful) {
                    // if isSuccessful then done message will be shown
                    // and you can change the password
                    Toast.makeText(this@ParentLogin, "Done sent", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@ParentLogin, "Error Occurred", Toast.LENGTH_LONG).show()
                }
            }).addOnFailureListener(OnFailureListener {
                loadingBar.dismiss()
                Toast.makeText(this@ParentLogin, "Error Failed", Toast.LENGTH_LONG).show()
            })
    }

    private fun getLogin() {
        pauth.signInWithEmailAndPassword(parentUsername, parentPassword)
            .addOnCompleteListener(this@ParentLogin) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Logged in successfully !", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ParentDashboard::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
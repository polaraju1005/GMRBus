package com.example.gmrbus.logins

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.gmrbus.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ParentRegistration : AppCompatActivity() {

    private lateinit var edtParentName: EditText
    private lateinit var edtParentEmail: EditText
    private lateinit var edtParentPhone: EditText
    private lateinit var etPaPassword: EditText
    private lateinit var etPaConfirmPassword: EditText
    private lateinit var paRegister: Button
    private lateinit var name: String
    lateinit var mail: String
    lateinit var personalPhone: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var auth: FirebaseAuth
    private var firebaseUserId: String = ""
    lateinit var refUsers: DatabaseReference
    private lateinit var dialog:Dialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_registration)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        title = "Register"

        edtParentName = findViewById(R.id.etParentRegName)
        edtParentEmail = findViewById(R.id.etParentRegEmail)
        edtParentPhone = findViewById(R.id.etParentRegMobNo)
        etPaPassword = findViewById(R.id.etParentRegPassword)
        etPaConfirmPassword = findViewById(R.id.etParentRegConfirmPassword)
        paRegister = findViewById(R.id.btnParentRegister)
        auth = FirebaseAuth.getInstance()


        paRegister.setOnClickListener {
            name = edtParentName.text.toString().trim { it <= ' ' }
            mail = edtParentEmail.text.toString().trim() { it <= ' ' }
            personalPhone = edtParentPhone.text.toString().trim() { it <= ' ' }
            password = etPaPassword.text.toString().trim() { it <= ' ' }
            confirmPassword = etPaConfirmPassword.text.toString().trim() { it <= ' ' }
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
            } else if (mail.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show()
            } else if (password.length < 8) {
                Toast.makeText(this, "Password length must be 8 characters", Toast.LENGTH_SHORT)
                    .show()
            } else if (confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please Enter confirm password", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(
                    this,
                    "password and confirm password should same",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                next()
            }
        }
    }

    private operator fun next() {
        showProgressBar()
        auth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this@ParentRegistration) { task ->
                if (task.isSuccessful) {
                    firebaseUserId = auth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("Parents")
                        .child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["username"] = edtParentName.text.toString().trim { it <= ' ' }
                    userHashMap["email"] = edtParentEmail.text.toString().trim { it <= ' ' }
                    userHashMap["phone"] = edtParentPhone.text.toString().trim { it <= ' ' }
                    refUsers.updateChildren(userHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Parent registration successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this, ParentLogin::class.java))
                            finish()
                        }
                    }
                } else {
                    hideProgressBar()
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showProgressBar() {
        dialog = Dialog(this@ParentRegistration)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}
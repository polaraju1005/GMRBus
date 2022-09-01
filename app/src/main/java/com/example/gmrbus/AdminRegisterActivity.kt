package com.example.gmrbus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import java.time.temporal.TemporalAdjusters.next

class AdminRegisterActivity : AppCompatActivity() {

    lateinit var logo: ImageView
    lateinit var txtBuses: TextView
    lateinit var textHey: TextView
    private lateinit var imgAdmin: ImageView
    private lateinit var edtUserName: EditText
    private lateinit var edtEmail: EditText
    lateinit var etPersonalPhone: EditText
    private lateinit var busroute: TextInputLayout
    private lateinit var etpassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var register: Button
    private lateinit var name: String
    lateinit var mail: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var personalPhone: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Register"

        logo = findViewById(R.id.imgGMR)
        txtBuses = findViewById(R.id.txtBuses)
        textHey = findViewById(R.id.txtHey)
        imgAdmin = findViewById(R.id.imgAdmin)
        edtUserName = findViewById(R.id.etUsername)
        edtEmail = findViewById(R.id.etEmail)
        busroute = findViewById(R.id.dropdownBusRoute)
        etPersonalPhone = findViewById(R.id.etPersonalPhn)
        etpassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etCnfPassword)
        register = findViewById(R.id.btnRegister)

        val busroutes = resources.getStringArray(R.array.busroute)

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_busroute, busroutes)

        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        autocompleteTV.setAdapter(arrayAdapter)

        register.setOnClickListener {
            name = edtUserName.text.toString().trim { it <= ' ' }
            mail = edtEmail.text.toString().trim { it <= ' ' }
            personalPhone = etPersonalPhone.text.toString().trim { it <= ' ' }
            password = etpassword.text.toString().trim { it <= ' ' }
            confirmPassword = etConfirmPassword.text.toString().trim { it <= ' ' }
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
            } else if (mail.isEmpty()) {
                Toast.makeText(this, "please enter mail", Toast.LENGTH_SHORT).show()
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

    private fun next() {
        Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    }
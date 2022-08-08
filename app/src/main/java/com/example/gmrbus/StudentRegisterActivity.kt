package com.example.gmrbus

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text
import java.time.temporal.TemporalAdjusters.next

class StudentRegisterActivity : AppCompatActivity() {
    lateinit var logo: ImageView
    lateinit var txtBuses: TextView
    lateinit var textHey: TextView
    private lateinit var imgStudent: ImageView
    private lateinit var edtUserName: EditText
    private lateinit var edtEmail: EditText
    lateinit var etParentPhone: EditText
    lateinit var etPersonalPhone: EditText
    lateinit var yos: TextInputLayout
    private lateinit var department: TextInputLayout
    private lateinit var etpassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var register: Button
    private lateinit var name: String
    lateinit var mail: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var parentPhone: String
    lateinit var personalPhone: String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Register"

        logo = findViewById(R.id.imgGMR)
        txtBuses = findViewById(R.id.txtBuses)
        textHey = findViewById(R.id.txtHey)
        imgStudent = findViewById(R.id.imgStudent)
        edtUserName = findViewById(R.id.etUsername)
        edtEmail = findViewById(R.id.etEmail)
        etParentPhone = findViewById(R.id.etParentPhn)
        etPersonalPhone = findViewById(R.id.etPersonalPhn)
        yos = findViewById(R.id.dropdown)
        department = findViewById(R.id.dropdownDepartment)
        etpassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etCnfPassword)
        register = findViewById(R.id.btnRegister)

        val years = resources.getStringArray(R.array.year_of_study)
        val departments = resources.getStringArray(R.array.department)

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, years)
        val arrayAdapterTwo = ArrayAdapter(this, R.layout.department_dropdown, departments)

        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val autoCompleteTV2 = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)

        autocompleteTV.setAdapter(arrayAdapter)
        autoCompleteTV2.setAdapter(arrayAdapterTwo)

        register.setOnClickListener {
            name = edtUserName.text.toString().trim { it <= ' ' }
            mail = edtEmail.text.toString().trim { it <= ' ' }
            parentPhone = etParentPhone.text.toString().trim { it <= ' ' }
            personalPhone = etPersonalPhone.text.toString().trim { it <= ' ' }
            password = etpassword.text.toString().trim { it <= ' ' }
            confirmPassword = etConfirmPassword.text.toString().trim { it <= ' ' }
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
            } else if (mail.isEmpty()) {
                Toast.makeText(this, "please enter mail", Toast.LENGTH_SHORT).show()
            } else if (parentPhone.isEmpty() && personalPhone.isEmpty()) {
                Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
            } else if (parentPhone.length < 10 && personalPhone.length < 10) {
                Toast.makeText(this, "Mobile number must contain 10 digits", Toast.LENGTH_SHORT)
                    .show()
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
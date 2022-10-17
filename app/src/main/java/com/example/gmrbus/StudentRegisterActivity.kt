package com.example.gmrbus

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudentRegisterActivity : AppCompatActivity() {
    lateinit var logo: ImageView
    lateinit var txtBuses: TextView
    lateinit var textHey: TextView
    private lateinit var imgStudent: ImageView
    private lateinit var edtUserName: EditText
    private lateinit var edtEmail: EditText
    lateinit var etParentPhone: EditText
    private lateinit var etPersonalPhone: EditText
    private lateinit var yos: TextInputLayout
    private lateinit var department: TextInputLayout
    lateinit var coordinator: TextInputLayout
    private lateinit var etpassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var register: Button
    private lateinit var name: String
    lateinit var mail: String
    lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var parentPhone: String
    lateinit var personalPhone: String
    lateinit var refusers: DatabaseReference
    private var firebaseUserId: String = ""
    private var adminUserId: String = ""
    lateinit var auth: FirebaseAuth


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
        coordinator = findViewById(R.id.dropdownAdmin)
        department = findViewById(R.id.dropdownDepartment)
        etpassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etCnfPassword)
        register = findViewById(R.id.btnRegister)
        auth = FirebaseAuth.getInstance()

        val years = resources.getStringArray(R.array.year_of_study)
        val departments = resources.getStringArray(R.array.department)
        val admins = resources.getStringArray(R.array.coordinator)

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, years)
        val arrayAdapterTwo = ArrayAdapter(this, R.layout.department_dropdown, departments)
        val arrayAdapterThree = ArrayAdapter(this, R.layout.dropdown_admin, admins)

        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val autoCompleteTV2 = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        val autoCompleteTV3 = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView3)

        autocompleteTV.setAdapter(arrayAdapter)
        autoCompleteTV2.setAdapter(arrayAdapterTwo)
        autoCompleteTV3.setAdapter(arrayAdapterThree)

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

    private operator fun next() {
        auth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this@StudentRegisterActivity) { task ->
                if (task.isSuccessful) {
                    firebaseUserId = auth.currentUser!!.uid
                    adminUserId = coordinator.editText!!.text.toString()
                    refusers =
                        FirebaseDatabase.getInstance().reference.child("Users").child(adminUserId)
                            .child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["username"] = edtUserName.text.toString().trim { it <= ' ' }
                    userHashMap["email"] = edtEmail.text.toString().trim { it <= ' ' }
                    userHashMap["parentPhone"] = etParentPhone.text.toString().trim { it <= ' ' }
                    userHashMap["phone"] = etPersonalPhone.text.toString().trim { it <= ' ' }
                    userHashMap["yos"] = yos.editText!!.text.toString()
                    userHashMap["department"] = department.editText!!.text.toString()
                    userHashMap["coordinator"] = coordinator.editText!!.text.toString()
                    refusers.updateChildren(userHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "User registration successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this,StudentLogin::class.java))
                        }
                    }
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
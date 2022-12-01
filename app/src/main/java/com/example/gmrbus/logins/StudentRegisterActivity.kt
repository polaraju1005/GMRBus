package com.example.gmrbus.logins

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.gmrbus.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudentRegisterActivity : AppCompatActivity() {

    private lateinit var edtUserName: EditText
    private lateinit var edtEmail: EditText
    lateinit var etParentPhone: EditText
    private lateinit var etPersonalPhone: EditText
    private lateinit var yos: TextInputLayout
    private lateinit var department: TextInputLayout
    lateinit var busNumber: TextInputLayout
    private lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var register: Button
    private lateinit var name: String
    lateinit var mail: String
    private lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var parentPhone: String
    lateinit var personalPhone: String
    lateinit var refUsers: DatabaseReference
    private var firebaseUserId: String = ""
    lateinit var mauth: FirebaseAuth
    private lateinit var dialog: Dialog


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Register"

        edtUserName = findViewById(R.id.etUsername)
        edtEmail = findViewById(R.id.etEmail)
        etParentPhone = findViewById(R.id.etParentPhn)
        etPersonalPhone = findViewById(R.id.etPersonalPhn)
        yos = findViewById(R.id.dropdown)
        busNumber = findViewById(R.id.dropdownBusNumber)
        department = findViewById(R.id.dropdownDepartment)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etCnfPassword)
        register = findViewById(R.id.btnRegister)
        mauth = FirebaseAuth.getInstance()

        val years = resources.getStringArray(R.array.year_of_study)
        val departments = resources.getStringArray(R.array.department)
        val busNumbers = resources.getStringArray(R.array.BusNumbers)

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, years)
        val arrayAdapterTwo = ArrayAdapter(this, R.layout.department_dropdown, departments)
        val arrayAdapterThree = ArrayAdapter(this, R.layout.dropdown_admin, busNumbers)

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
            password = etPassword.text.toString().trim { it <= ' ' }
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
                regNext()
            }
        }

    }

    private  fun regNext() {
        showProgressBar()
        mauth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this@StudentRegisterActivity) { task ->
                if (task.isSuccessful) {
                    firebaseUserId = mauth.currentUser!!.uid
                    refUsers =
                        FirebaseDatabase.getInstance().reference.child("Users")
                            .child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["username"] = edtUserName.text.toString().trim { it <= ' ' }
                    userHashMap["email"] = edtEmail.text.toString().trim { it <= ' ' }
                    userHashMap["parentPhone"] = etParentPhone.text.toString().trim { it <= ' ' }
                    userHashMap["phone"] = etPersonalPhone.text.toString().trim { it <= ' ' }
                    userHashMap["yos"] = yos.editText!!.text.toString()
                    userHashMap["department"] = department.editText!!.text.toString()
                    userHashMap["busNumber"] = busNumber.editText!!.text.toString()
                    refUsers.updateChildren(userHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "User registration successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this, StudentLogin::class.java))
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
        dialog = Dialog(this@StudentRegisterActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}
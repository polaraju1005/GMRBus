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

class AdminRegisterActivity : AppCompatActivity() {

//    private lateinit var adLogo: ImageView
//    lateinit var adTxtBuses: TextView
//    lateinit var adTextHey: TextView
//    private lateinit var imgAdmin: ImageView
    private lateinit var edtAdminName: EditText
    private lateinit var edtAdminEmail: EditText
    lateinit var etAdminPhone: EditText
    private lateinit var busroute: TextInputLayout
    private lateinit var routeNumber: TextInputLayout
    private lateinit var etAdPassword: EditText
    lateinit var etAdConfirmPassword: EditText
    lateinit var adRegister: Button
    private lateinit var name: String
    lateinit var mail: String
    private lateinit var password: String
    lateinit var confirmPassword: String
    lateinit var personalPhone: String
    lateinit var auth: FirebaseAuth
    private var firebaseUserId: String = ""
    lateinit var refUsers: DatabaseReference
    private lateinit var dialog: Dialog
    private var adminNumber:Int = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Register"

//        adLogo = findViewById(R.id.adImgGMR)
//        adTxtBuses = findViewById(R.id.adTxtBuses)
//        adTextHey = findViewById(R.id.adTxtHey)
//        imgAdmin = findViewById(R.id.imgAdmin)
        edtAdminName = findViewById(R.id.etAdminName)
        edtAdminEmail = findViewById(R.id.etAdminEmail)
        busroute = findViewById(R.id.dropdownBusRoute)
        routeNumber = findViewById(R.id.dropdownRouteNumber)
        etAdminPhone = findViewById(R.id.etAdminPhn)
        etAdPassword = findViewById(R.id.etAdPassword)
        etAdConfirmPassword = findViewById(R.id.etAdCnfPassword)
        adRegister = findViewById(R.id.btnAdRegister)
        auth = FirebaseAuth.getInstance()

        val busRoutes = resources.getStringArray(R.array.busroute)
        val routeNumbers = resources.getStringArray(R.array.RouteNumber)

        val arrayAdapter3 = ArrayAdapter(this, R.layout.dropdown_busroute, busRoutes)
        val arrayAdapter4 = ArrayAdapter(this, R.layout.dropdown_busroute, routeNumbers)

        val autocompleteTV3 = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView2)
        val autoCompleteTV4 = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView4)

        autocompleteTV3.setAdapter(arrayAdapter3)
        autoCompleteTV4.setAdapter(arrayAdapter4)

        adRegister.setOnClickListener {
            name = edtAdminName.text.toString().trim { it <= ' ' }
            mail = edtAdminEmail.text.toString().trim { it <= ' ' }
            personalPhone = etAdminPhone.text.toString().trim { it <= ' ' }
            password = etAdPassword.text.toString().trim { it <= ' ' }
            confirmPassword = etAdConfirmPassword.text.toString().trim { it <= ' ' }
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
                adminNumber++
                next()
            }
        }
    }

    private operator fun next() {
        showProgressBar()
        auth.createUserWithEmailAndPassword(mail, password)
            .addOnCompleteListener(this@AdminRegisterActivity) { task ->
                if (task.isSuccessful) {
                    firebaseUserId = auth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("Admins")
                        .child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["username"] = edtAdminName.text.toString().trim { it <= ' ' }
                    userHashMap["email"] = edtAdminEmail.text.toString().trim { it <= ' ' }
                    userHashMap["phone"] = etAdminPhone.text.toString().trim { it <= ' ' }
                    userHashMap["busRoute"] = busroute.editText!!.text.toString()
                    userHashMap["routeNumber"] = routeNumber.editText!!.text.toString()
                    userHashMap["adminNumber"] = adminNumber.toString()
                    refUsers.updateChildren(userHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "User registration successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this, AdminLogin::class.java))
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
        dialog = Dialog(this@AdminRegisterActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }

}
package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etName: EditText
    private lateinit var btnSignup: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var rtdb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etName = findViewById(R.id.etName)
        btnSignup = findViewById(R.id.btnSignup)

        btnSignup.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            signup(name, email, password)
        }
    }

    private fun signup(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    addUserToDatabase(name, email, auth.currentUser?.uid!!)
                    Intent(this@SignupActivity, MainActivity::class.java).also {
                        finish()
                        startActivity(it)
                    }
                }
                else{
                    Toast.makeText(this@SignupActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun addUserToDatabase(name: String, email: String, uid: String) {
        rtdb = FirebaseDatabase.getInstance().getReference()
        rtdb.child("user").child(uid).setValue(User(name,email,uid))
    }
}
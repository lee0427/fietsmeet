package com.example.fietsmeet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance("https://use-q2-app-default-rtdb.europe-west1.firebasedatabase.app").reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()



        val emailInput = findViewById<EditText>(R.id.idEdtSignUpEmail)
        val usernameInput = findViewById<EditText>(R.id.idEdtSignUpUsername)
        val phoneInput = findViewById<EditText>(R.id.idEdtSignUpPhone)
        val passwordInput = findViewById<EditText>(R.id.idEdtSignUpPassword)
        val signUpButton = findViewById<Button>(R.id.idBtnSignUp)
        val loginText = findViewById<TextView>(R.id.tvAlreadyHaveAccount)

        // Handle Sign-Up Button Click
        signUpButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val username = usernameInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase Authentication Sign-Up
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        // Send email verification
                        saveUserToDatabase(user.uid, email, username, phone)
                        user.sendEmailVerification()
                        Toast.makeText(this, "Account created! Verify your email.", Toast.LENGTH_LONG).show()

                        // Redirect to Login Page
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Failed to create account: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Handle "Already have an account? Login" Click
        loginText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveUserToDatabase(userId: String, email: String, username: String, phone: String) {
        val user = mapOf(
            "email" to email,
            "username" to username,
            "phone" to phone
        )

        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                Log.d("FirebaseDB", "User added to database: $userId")
                Toast.makeText(this, "User saved successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseDB", "Database write failed: ${e.message}")
                Toast.makeText(this, "Database write failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeUserInDatabase(userId: String, username: String, email: String, phone: String) {
        val userObject = mapOf(
            "username" to username,
            "email" to email,
            "phone" to phone,
            "friends" to emptyMap<String, Boolean>() // Empty friends list
        )

        database.child("users").child(userId).setValue(userObject)
            .addOnSuccessListener {
                Toast.makeText(this, "User saved in database!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseDB", "Failed to save user: ${e.message}")
                Toast.makeText(this, "Failed to save user: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

package com.example.fietsmeet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val emailInput = findViewById<EditText>(R.id.idEdtSignUpEmail)
        val usernameInput = findViewById<EditText>(R.id.idEdtSignUpUsername)
        val phoneInput = findViewById<EditText>(R.id.idEdtSignUpPhone)
        val passwordInput = findViewById<EditText>(R.id.idEdtSignUpPassword)
        val signUpButton = findViewById<Button>(R.id.idBtnSignUp)
        val loginText = findViewById<TextView>(R.id.tvAlreadyHaveAccount)

        // Handle Sign-Up Button Click
        signUpButton.setOnClickListener {
            val email = emailInput.text.toString()
            val username = usernameInput.text.toString()
            val phone = phoneInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isEmpty() || username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Save user credentials (Temporary, replace with Firebase/Auth API later)
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()

                // Redirect to Login Page
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Handle "Already have an account? Login" Click
        loginText.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

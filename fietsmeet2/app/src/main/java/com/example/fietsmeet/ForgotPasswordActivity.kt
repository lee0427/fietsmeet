package com.example.fietsmeet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emailInput = findViewById<EditText>(R.id.idEdtForgotEmail)
        val resetButton = findViewById<Button>(R.id.idBtnResetPassword)

        resetButton.setOnClickListener {
            val email = emailInput.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                // Here, you would typically send an email to reset the password (if using Firebase/Auth API)
                Toast.makeText(this, "Reset link sent to $email", Toast.LENGTH_SHORT).show()

                // Redirect user back to login page
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

package com.example.fietsmeet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val welcomeText = findViewById<TextView>(R.id.tvWelcome)
        val logoutButton = findViewById<Button>(R.id.idBtnLogout)

        // Get the user’s email from the intent
        val userEmail = intent.getStringExtra("EMAIL_KEY")
        welcomeText.text = "Welcome, $userEmail!"

        // Logout Button Click: Return to Login Page
        logoutButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java) // Redirect to login screen
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears activity stack
            startActivity(intent)
            finish()
        }
    }
}

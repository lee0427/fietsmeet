package com.example.fietsmeet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
    }

    private lateinit var sharedpreferences: SharedPreferences
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize Shared Preferences
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        email = sharedpreferences.getString(EMAIL_KEY, "User")

        // Set Welcome Message
        val welcomeTV = findViewById<TextView>(R.id.idTVWelcome)
        welcomeTV.text = "Welcome, $email!"

        // Logout Button
        val logoutBtn = findViewById<Button>(R.id.idBtnLogout)
        logoutBtn.setOnClickListener {
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    Toast.makeText(this, "Explore Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_friends -> {
                    // ✅ Open Friends Page
                    val intent = Intent(this, FriendsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_community -> {
                    Toast.makeText(this, "Community Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}

//package com.example.fietsmeet
//
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.view.MenuItem
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class HomeActivity : AppCompatActivity() {
//
//    companion object {
//        const val SHARED_PREFS = "shared_prefs"
//        const val EMAIL_KEY = "email_key"
//    }
//
//    private lateinit var sharedpreferences: SharedPreferences
//    private var email: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        // Initialize Shared Preferences
//        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
//        email = sharedpreferences.getString(EMAIL_KEY, "User")
//
//        // Set Welcome Message
//        val welcomeTV = findViewById<TextView>(R.id.idTVWelcome)
//        welcomeTV.text = "Welcome, $email!"
//
//        // Logout Button
//        val logoutBtn = findViewById<Button>(R.id.idBtnLogout)
//        logoutBtn.setOnClickListener {
//            val editor = sharedpreferences.edit()
//            editor.clear()
//            editor.apply()
//
//            val intent = Intent(this, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }
//
//        // Bottom Navigation
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNav.setOnNavigationItemSelectedListener { item: MenuItem ->
//            when (item.itemId) {
//                R.id.nav_explore -> {
//                    Toast.makeText(this, "Explore Selected", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_friends -> {
//                    Toast.makeText(this, "Friends Selected", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_community -> {
//                    Toast.makeText(this, "Community Selected", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.nav_settings -> {
//                    Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//}
//
//

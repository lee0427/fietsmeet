package com.example.fietsmeet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    // creating constant keys for shared preferences.
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }

    // variable for shared preferences.
    private lateinit var sharedpreferences: SharedPreferences
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        // getting data from shared prefs and
        // storing it in our string variable.
        email = sharedpreferences.getString(EMAIL_KEY, null)

        // initializing our textview and button.
        val welcomeTV = findViewById<TextView>(R.id.idTVWelcome)
//        welcomeTV.text = "Welcome email"
        welcomeTV.text = getString(R.string.welcome_message, email)
        val logoutBtn = findViewById<Button>(R.id.idBtnLogout)
        logoutBtn.setOnClickListener {
            // calling method to edit values in shared prefs.
            val editor = sharedpreferences.edit()

            // below line will clear
            // the data in shared prefs.
            editor.clear()

            // below line will apply empty
            // data to shared prefs.
            editor.apply()

            // starting mainactivity after
            // clearing values in shared preferences.
            val i = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}

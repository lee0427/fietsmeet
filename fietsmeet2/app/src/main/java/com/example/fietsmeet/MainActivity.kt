package com.example.fietsmeet
//from template
import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.fietsmeet.ui.theme.FietsmeetTheme
////my imports
import android.widget.ImageView
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }

    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing EditTexts and our Button
        val emailEdt = findViewById<EditText>(R.id.idEdtEmail)
        val passwordEdt = findViewById<EditText>(R.id.idEdtPassword)
        val loginBtn = findViewById<Button>(R.id.idBtnLogin)
        val forgotPasswordTV = findViewById<TextView>(R.id.tvForgotPassword) // ADD THIS LINE
        firebaseAuth = FirebaseAuth.getInstance()

        // Add click listener to "Forgot Password?" TextView
        forgotPasswordTV.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        val signUpText = findViewById<TextView>(R.id.tvSignUp) // Find Sign Up TextView

        signUpText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }


        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        // in shared prefs inside get string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        email = sharedpreferences.getString(EMAIL_KEY, null)
        password = sharedpreferences.getString(PASSWORD_KEY, null)

        // calling on click listener for login button.
        loginBtn.setOnClickListener {
            // to check if the user fields are empty or not.
            if (TextUtils.isEmpty(emailEdt.text.toString()) && TextUtils.isEmpty(passwordEdt.text.toString())) {
                // this method will call when email and password fields are empty.
                Toast.makeText(this@MainActivity, "Please Enter Email and Password", Toast.LENGTH_SHORT).show()
            } else {
                val editor = sharedpreferences.edit()

                // below two lines will put values for
                // email and password in shared preferences.
                editor.putString(EMAIL_KEY, emailEdt.text.toString())
                editor.putString(PASSWORD_KEY, passwordEdt.text.toString())

                // to save our data with key and value.
                editor.apply()

                firebaseAuth.signInWithEmailAndPassword(emailEdt.text.toString(), passwordEdt.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // if the task is successful we are
                        // displaying a success toast message.
                        val testRef = FirebaseDatabase.getInstance()
                            .getReference("test")

                        testRef.setValue("Hello Firebase!")
                            .addOnSuccessListener {
                                Log.d("FirebaseDB", "✅ Successfully wrote test data!")
                            }
                            .addOnFailureListener { e ->
                                Log.e("FirebaseDB", "❌ Firebase test failed: ${e.message}")
                            }

                        Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()

                        // starting new activity.
                        val i = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        // if the task is not successful we are
                        // displaying a failure toast message.
                        Toast.makeText(this@MainActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    override fun onStart() {
        super.onStart()
        if (email != null && password != null) {
            val i = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(i)
        }
    }
}
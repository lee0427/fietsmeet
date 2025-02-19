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
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREFS = "shared_prefs"
        const val EMAIL_KEY = "email_key"
        const val PASSWORD_KEY = "password_key"
    }

    private lateinit var sharedpreferences: SharedPreferences
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing EditTexts and our Button
        val emailEdt = findViewById<EditText>(R.id.idEdtEmail)
        val passwordEdt = findViewById<EditText>(R.id.idEdtPassword)
        val loginBtn = findViewById<Button>(R.id.idBtnLogin)

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

                // starting new activity.
                val i = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
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


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            FietsmeetTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    FietsmeetTheme {
//        Greeting("Android")
//    }
}
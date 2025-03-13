package com.example.fietsmeet

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FriendsActivity : AppCompatActivity() {

    private val friendsList = listOf(
        "Michael_snelFiets",
        "Mykolas_hedge",
        "Bo_the_hedgehog_slayer",
        "tino_the_survivor",
        "giuliano_hedge_friendo",
        "sten_in_vacay"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        // Set up ListView for friends
        val listView = findViewById<ListView>(R.id.friendsListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, friendsList)
        listView.adapter = adapter

        // Click listener to open ChatActivity
        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("friend_name", friendsList[position])
            startActivity(intent)
        }

        // Initialize Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Prevent crash: Ensure `bottomNav` is not null
        bottomNav?.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_friends -> true // Stay on Friends Page
                R.id.nav_community -> {
                    Toast.makeText(this, "Community Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Prevent crash by setting default selection safely
        bottomNav?.selectedItemId = R.id.nav_friends
    }
}

//package com.example.fietsmeet
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import android.widget.ListView
//import androidx.appcompat.app.AppCompatActivity
//
//class FriendsActivity : AppCompatActivity() {
//
//    private val friendsList = listOf(
//        "Michael_snelFiets",
//        "Mykolas_hedge",
//        "Bo_the_hedgehog_slayer",
//        "tino_the_survivor",
//        "giuliano_hedge_friendo",
//        "sten_in_vacay"
//    ) // Example list of friends
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_friends)
//
//        // Find ListView
//        val listView = findViewById<ListView>(R.id.friendsListView)
//
//        // Set Adapter to Display Friends
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, friendsList)
//        listView.adapter = adapter
//
//        // Click listener for friend items to open chat
//        listView.setOnItemClickListener { _, _, position, _ ->
//            val intent = Intent(this, ChatActivity::class.java)
//            intent.putExtra("friend_name", friendsList[position]) // Pass friend name
//            startActivity(intent)
//        }
//    }
//}

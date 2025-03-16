package com.example.fietsmeet

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChallengesActivity : AppCompatActivity() {

    private lateinit var challengeAdapter: ChallengeAdapter
    private lateinit var challengeList: List<Challenge>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenges)

        // Sample Data
        challengeList = listOf(
            Challenge("Meet 2 other users", "50% accomplished", 50, "Ends in 2 days"),
            Challenge("Feed your hedgehog", "0% accomplished", 0, "Ends in 5 days"),
            Challenge("Win a challenge", "75% accomplished", 75, "Ends in 1 day")
        )

        // RecyclerView Setup
        val recyclerView = findViewById<RecyclerView>(R.id.challengesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        challengeAdapter = ChallengeAdapter(challengeList)
        recyclerView.adapter = challengeAdapter

        // Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_explore -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.nav_friends -> startActivity(Intent(this, FriendsActivity::class.java))
                R.id.nav_community -> true // Stay on this page
                R.id.nav_settings -> Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
            }
            true
        }
        bottomNav.selectedItemId = R.id.nav_community
    }
}







//package com.example.fietsmeet
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.MenuItem
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class ChallengesActivity : AppCompatActivity() {
//
//    private lateinit var challengeAdapter: ChallengeAdapter
//    private lateinit var challengeList: List<Challenge>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_challenges)
//
//        // Sample Data
//        challengeList = listOf(
//            Challenge("Meet 2 other users", "50% accomplished", 50, "Ends in 2 days"),
//            Challenge("Feed your hedgehog", "0% accomplished", 0, "Ends in 5 days"),
//            Challenge("Win a challenge", "75% accomplished", 75, "Ends in 1 day")
//        )
//
//        // RecyclerView Setup
//        val recyclerView = findViewById<RecyclerView>(R.id.challengesRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        challengeAdapter = ChallengeAdapter(challengeList)
//        recyclerView.adapter = challengeAdapter
//
//        // ✅ Bottom Navigation Setup
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNav?.setOnItemSelectedListener { item: MenuItem ->
//            when (item.itemId) {
//                R.id.nav_explore -> {
//                    startActivity(Intent(this, HomeActivity::class.java))
//                    finish()
//                    true
//                }
//                R.id.nav_friends -> {
//                    startActivity(Intent(this, FriendsActivity::class.java))
//                    finish()
//                    true
//                }
//                R.id.nav_community -> true // Stay on the same page
//                R.id.nav_settings -> {
//                    Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        // ✅ Highlight "Challenges" as selected
//        bottomNav?.selectedItemId = R.id.nav_community
//    }
//}

package com.example.fietsmeet

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Retrieve the friend's name from Intent
        val friendName = intent.getStringExtra("friend_name")

        // Set up the chat screen title
        val chatTitle = findViewById<TextView>(R.id.chatTitle)
        chatTitle.text = "Chat with $friendName"
    }
}

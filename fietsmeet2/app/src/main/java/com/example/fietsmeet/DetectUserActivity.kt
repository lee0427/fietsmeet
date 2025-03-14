package com.example.fietsmeet

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.*

class DetectUserActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference
    private val currentUserID = "4t6Mii0fkZWQ742WKRLGmajBPWE3" // Replace with actual user ID from Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().getReference("detected_users/$currentUserID")

        // Listen for detected users in Firebase
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val detectedUserID = snapshot.child("detected_user").getValue(String::class.java)
                detectedUserID?.let {
                    showConnectionRequestDialog(it)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read detected users", error.toException())
            }
        })
    }

    private fun showConnectionRequestDialog(detectedUserID: String) {
        AlertDialog.Builder(this)
            .setTitle("New Encounter")
            .setMessage("You have encountered $detectedUserID during your bike ride. Do you want to connect?")
            .setPositiveButton("Yes") { _, _ ->
                addFriend(detectedUserID)
            }
            .setNegativeButton("No") { _, _ ->
                removeDetectedUser(detectedUserID)
            }
            .show()
    }

    private fun addFriend(detectedUserID: String) {
        val friendRef = FirebaseDatabase.getInstance().getReference("friendships")

        // Add friendship for both users
        friendRef.child(currentUserID).child(detectedUserID).setValue(true)
        friendRef.child(detectedUserID).child(currentUserID).setValue(true)

        Toast.makeText(this, "You are now friends with $detectedUserID!", Toast.LENGTH_SHORT).show()

        // Remove from detected list
        removeDetectedUser(detectedUserID)
    }

    private fun removeDetectedUser(detectedUserID: String) {
        database.child(detectedUserID).removeValue()
    }
}

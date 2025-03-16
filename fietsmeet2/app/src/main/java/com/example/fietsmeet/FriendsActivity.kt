package com.example.fietsmeet

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FriendsActivity : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance("https://use-q2-app-default-rtdb.europe-west1.firebasedatabase.app").reference
    private lateinit var friendRequestsRef: DatabaseReference
    private lateinit var friendshipsRef: DatabaseReference
    private lateinit var detectedUsersRef: DatabaseReference
    private lateinit var friendsListAdapter: ArrayAdapter<String>

    private val friendsList = mutableListOf<String>()
    private val currentUserID = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        val listView = findViewById<ListView>(R.id.friendsListView)
        val fabAddFriend = findViewById<FloatingActionButton>(R.id.addFriendButton)

        friendsListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, friendsList)
        listView.adapter = friendsListAdapter

        friendRequestsRef = database.child("friend_requests")
        friendshipsRef = database.child("friendships")
        detectedUsersRef = database.child("detected_devices")

        listenForFriendships()
        listenForIncomingRequests()
        listenForDetectedUsers()

        // Open Bottom Sheet when FAB is clicked
        fabAddFriend.setOnClickListener {
            val userSearchFragment = UserSearchFragment()
            userSearchFragment.show(supportFragmentManager, "UserSearchFragment")
        }


        // Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_friends -> true
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
        bottomNav?.selectedItemId = R.id.nav_friends
    }

    // Step 1: Listen for Accepted Friendships
    private fun listenForFriendships() {
        friendshipsRef.child(currentUserID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                friendsList.clear()
                for (friendSnapshot in snapshot.children) {
                    val friendUserID = friendSnapshot.key ?: continue
                    fetchUserName(friendUserID) // Fetch username instead of showing user ID
                }
                friendsListAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // Step 2: Listen for Incoming Friend Requests
    private fun listenForIncomingRequests() {
        friendRequestsRef.child(currentUserID).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val senderUserId = snapshot.key
                if (senderUserId != null) {
                    fetchSenderInfo(senderUserId)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        })
    }

    // Step 3: Listen for Detected Users
    private fun listenForDetectedUsers() {
        detectedUsersRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val detectedUserID = snapshot.child("detected_user").value.toString()
                if (detectedUserID.isNotEmpty()) {
                    checkIfUserExists(detectedUserID)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        })
    }

    // Step 2.1: Fetch Sender's Information & Show Accept/Decline Dialog
    private fun fetchSenderInfo(senderUserId: String) {
        val usersRef = database.child("users").child(senderUserId)

        usersRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val senderUsername = snapshot.child("username").value.toString()
                showFriendRequestDialog(senderUserId, senderUsername)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to fetch user info.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchUserName(userID: String) {
        val usersRef = database.child("users").child(userID)

        usersRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val username = snapshot.child("username").value.toString()
                friendsList.add(username) // Add username instead of ID
                friendsListAdapter.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to fetch username", Toast.LENGTH_SHORT).show()
        }
    }

    // Step 2.2: Show AlertDialog for Friend Request
    private fun showFriendRequestDialog(senderUserId: String, senderUsername: String) {
        AlertDialog.Builder(this)
            .setTitle("Friend Request")
            .setMessage("$senderUsername sent you a friend request. Accept?")
            .setPositiveButton("Accept") { _, _ ->
                acceptFriendRequest(senderUserId)
                removeFriendRequest(senderUserId) // ✅ Remove request after accepting
            }
            .setNegativeButton("Decline") { _, _ ->
                declineFriendRequest(senderUserId)
                removeFriendRequest(senderUserId) // ✅ Remove request after declining
            }
            .setCancelable(false)
            .show()
    }



    // Step 4: Check if Detected User Exists
    private fun checkIfUserExists(userId: String) {
        val usersRef = database.child("users")

        usersRef.child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val username = snapshot.child("username").value.toString()
                showFriendshipPopup(userId, username)
            }
        }
    }

    // Step 5: Show Friend Request Popup
    private fun showFriendshipPopup(detectedUserId: String, username: String) {
        AlertDialog.Builder(this)
            .setTitle("New Connection Request")
            .setMessage("You encountered $username! Do you want to connect?")
            .setPositiveButton("Yes") { _, _ ->
                sendFriendRequest(detectedUserId)
            }
            .setNegativeButton("No") { _, _ ->
                removeDetection(detectedUserId)
            }
            .setCancelable(false)
            .show()
    }

    // Step 6: Send Friend Request
    private fun sendFriendRequest(detectedUserId: String) {
        val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid ?: return

        friendRequestsRef.child(detectedUserId).child(currentUserUID).setValue("pending")
            .addOnSuccessListener {
                Toast.makeText(this, "Friend request sent!", Toast.LENGTH_SHORT).show()
                removeDetection(detectedUserId) // removes after request sent
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to send request", Toast.LENGTH_SHORT).show()
            }
    }

    // Step 7: Accept Friend Request
    private fun acceptFriendRequest(senderUserId: String) {
        friendshipsRef.child(currentUserID).child(senderUserId).setValue(true)
        friendshipsRef.child(senderUserId).child(currentUserID).setValue(true)

        friendRequestsRef.child(currentUserID).child(senderUserId).removeValue()
        friendRequestsRef.child(senderUserId).child(currentUserID).removeValue()

        Toast.makeText(this, "You are now friends with $senderUserId!", Toast.LENGTH_SHORT).show()
        listenForFriendships()
    }

    // Step 8: Decline Friend Request
    private fun declineFriendRequest(senderUserId: String) {
        friendRequestsRef.child(currentUserID).child(senderUserId).removeValue()
        friendRequestsRef.child(senderUserId).child(currentUserID).removeValue()

        Toast.makeText(this, "Friend request declined.", Toast.LENGTH_SHORT).show()
    }

    // Step 9: Remove Detection Once Processed
    private fun removeDetection(detectedUserId: String) {
        val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid ?: return

        detectedUsersRef.child(currentUserUID).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Detected user removed.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to remove detected user: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun removeFriendRequest(senderUserId: String) {
        val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid ?: return
        friendRequestsRef.child(currentUserUID).child(senderUserId).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Friend request removed.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to remove request.", Toast.LENGTH_SHORT).show()
            }
    }


}

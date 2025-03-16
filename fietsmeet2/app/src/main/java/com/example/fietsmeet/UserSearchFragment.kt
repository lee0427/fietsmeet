package com.example.fietsmeet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*

class UserSearchFragment : BottomSheetDialogFragment() {

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.frament_user_search, container, false)

        val searchInput = view.findViewById<EditText>(R.id.editTextSearchUser)
        val searchButton = view.findViewById<Button>(R.id.buttonSearchUser)

        database = FirebaseDatabase.getInstance("https://use-q2-app-default-rtdb.europe-west1.firebasedatabase.app").reference.child("users")

        searchButton.setOnClickListener {
            val userID = searchInput.text.toString().trim()
            if (userID.isNotEmpty()) {
                searchUser(userID)
            } else {
                Toast.makeText(requireContext(), "User ID cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun searchUser(username: String) {
        val usersRef = FirebaseDatabase.getInstance()
            .getReference("users")

        usersRef.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val userID = userSnapshot.key ?: continue
                            val foundUsername = userSnapshot.child("username").value.toString()

                            // Show UserProfileFragment with found user
                            val fragment = UserProfileFragment.newInstance(userID, foundUsername)
                            fragment.show(parentFragmentManager, "UserProfileFragment")
                            return
                        }
                    } else {
                        Toast.makeText(requireContext(), "User not found!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error searching user", Toast.LENGTH_SHORT).show()
                }
            })
    }


    private fun showUserProfileDialog(userID: String, username: String) {
        val fragment = UserProfileFragment.newInstance(userID, username)
        fragment.show(parentFragmentManager, "UserProfileFragment")
    }
}

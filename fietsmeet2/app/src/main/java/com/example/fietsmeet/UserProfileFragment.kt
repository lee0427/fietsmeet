package com.example.fietsmeet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserProfileFragment : BottomSheetDialogFragment() {

    private lateinit var database: DatabaseReference
    private lateinit var friendRequestsRef: DatabaseReference
    private val currentUserID = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    companion object {
        private const val ARG_USER_ID = "userID"
        private const val ARG_USERNAME = "username"

        fun newInstance(userID: String, username: String): UserProfileFragment {
            val fragment = UserProfileFragment()
            val args = Bundle()
            args.putString(ARG_USER_ID, userID)
            args.putString(ARG_USERNAME, username)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        val userId = arguments?.getString(ARG_USER_ID) ?: ""
        val username = arguments?.getString(ARG_USERNAME) ?: ""

        val textUsername = view.findViewById<TextView>(R.id.textUsername)
        val textUserId = view.findViewById<TextView>(R.id.textUserId)
        val buttonSendRequest = view.findViewById<Button>(R.id.buttonSendFriendRequest)

        database = FirebaseDatabase.getInstance("https://use-q2-app-default-rtdb.europe-west1.firebasedatabase.app").reference
        friendRequestsRef = database.child("friend_requests")

        textUsername.text = username
        textUserId.text = "User ID: $userId"

        buttonSendRequest.setOnClickListener {
            sendFriendRequest(userId)
        }

        return view
    }

    private fun sendFriendRequest(targetUserID: String) {
        if (currentUserID == targetUserID) {
            Toast.makeText(requireContext(), "You cannot send a friend request to yourself!", Toast.LENGTH_SHORT).show()
            return
        }

        friendRequestsRef.child(targetUserID).child(currentUserID).setValue("pending")
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Friend request sent!", Toast.LENGTH_SHORT).show()
                dismiss()  // Close the fragment
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to send request", Toast.LENGTH_SHORT).show()
            }
    }
}

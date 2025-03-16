package com.example.fietsmeet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChallengeAdapter(private val challenges: List<Challenge>) :
    RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    class ChallengeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val challengeTitle: TextView = view.findViewById(R.id.challengeTitle)
        val challengeDescription: TextView = view.findViewById(R.id.challengeDescription)
        val challengeProgress: ProgressBar = view.findViewById(R.id.challengeProgressBar)
        val challengeProfile: ImageView = view.findViewById(R.id.challengeProfileImage)
        val challengeTimeLeft: TextView = view.findViewById(R.id.challengeTimeLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.challengeTitle.text = challenge.title
        holder.challengeDescription.text = challenge.description
        holder.challengeProgress.progress = challenge.progress
        holder.challengeTimeLeft.text = challenge.timeLeft
//        holder.challengeProfile.setImageResource(challenge.profileImageResId)
    }

    override fun getItemCount() = challenges.size
}

package com.example.freesudoku

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StatsActivity: AppCompatActivity() {

    var database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val gamesPlayedTextView: TextView = findViewById(R.id.GamesPlayedTextView)

        //Set totalGamesPlayed from FireBase
        val getData = object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value
                val totalGamesPlayedText = "Total Games Played: ${data?.toString()?.toInt() ?: 0}"
                gamesPlayedTextView.text = totalGamesPlayedText
            }
        }
        database.addValueEventListener(getData)
        database.addListenerForSingleValueEvent(getData)


    }

}
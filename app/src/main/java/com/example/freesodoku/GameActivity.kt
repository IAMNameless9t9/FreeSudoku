package com.example.freesodoku

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "ButtonClicked:"

class GameActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var oneButton: Button = findViewById(R.id.oneButton)
        oneButton.setOnClickListener(this)
        var twoButton: Button = findViewById(R.id.twoButton)
        twoButton.setOnClickListener(this)
        var threeButton: Button = findViewById(R.id.threeButton)
        threeButton.setOnClickListener(this)
        var fourButton: Button = findViewById(R.id.fourButton)
        fourButton.setOnClickListener(this)
        var fiveButton: Button = findViewById(R.id.fiveButton)
        fiveButton.setOnClickListener(this)
        var sixButton: Button = findViewById(R.id.sixButton)
        sixButton.setOnClickListener(this)
        var sevenButton: Button = findViewById(R.id.sevenButton)
        sevenButton.setOnClickListener(this)
        var eightButton: Button = findViewById(R.id.eightButton)
        eightButton.setOnClickListener(this)
        var nineButton: Button = findViewById(R.id.nineButton)
        nineButton.setOnClickListener(this)

    }

    override fun onClick(v: View){
        when(v.id){
            R.id.oneButton -> Log.d(TAG,"oneButton!")
            R.id.twoButton -> Log.d(TAG,"twoButton!")
            R.id.threeButton -> Log.d(TAG,"threeButton!")
            R.id.fourButton -> Log.d(TAG,"fourButton!")
            R.id.fiveButton -> Log.d(TAG,"fiveButton!")
            R.id.sixButton -> Log.d(TAG,"sixButton!")
            R.id.sevenButton -> Log.d(TAG,"sevenButton!")
            R.id.eightButton -> Log.d(TAG,"eightButton!")
            R.id.nineButton -> Log.d(TAG,"nineButton!")
        }
    }

}
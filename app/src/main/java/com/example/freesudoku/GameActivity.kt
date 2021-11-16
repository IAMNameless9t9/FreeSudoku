package com.example.freesudoku

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "ButtonClicked:"

class GameActivity : AppCompatActivity(), View.OnClickListener {

    var currentNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val inputButtons = arrayOf(R.id.oneButton, R.id.twoButton, R.id.threeButton, R.id.fourButton,
            R.id.fiveButton, R.id.sixButton, R.id.sevenButton, R.id.eightButton, R.id.nineButton)

        for (button in inputButtons){
            var b: Button = findViewById(button)
            b.setOnClickListener(this)
        }

        /*val rooms = arrayOf(R.id.topLeftRoom, R.id.topCenterRoom, R.id.topRightRoom,
        R.id.middleLeftRoom, R.id.middleCenterRoom, R.id.middleRightRoom,
        R.id.bottomLeftRoom, R.id.bottomCenterRoom, R.id.bottomRightRoom)

        for (room in rooms){
            var r: RoomLayout = findViewById(room)
            r.setOnClickListener {
                callbacks?.updateCurrentNum(currentNum)
            }
        }*/
    }

    interface Callbacks {
        fun updateCurrentNum(n: Int)
    }

    private var callbacks: Callbacks? = null

    override fun onClick(v: View){
        when(v.id){
            R.id.oneButton -> currentNum = 1
            R.id.twoButton -> currentNum = 2
            R.id.threeButton -> currentNum = 3
            R.id.fourButton -> currentNum = 4
            R.id.fiveButton -> currentNum = 5
            R.id.sixButton -> currentNum = 6
            R.id.sevenButton -> currentNum = 7
            R.id.eightButton -> currentNum = 8
            R.id.nineButton -> currentNum = 9
        }
        Log.d(TAG, "CurrentNum: $currentNum")
        TODO("This is not doing anything?")
        callbacks?.updateCurrentNum(this.currentNum)
    }

}
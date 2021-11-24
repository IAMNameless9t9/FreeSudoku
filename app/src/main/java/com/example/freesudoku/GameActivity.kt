package com.example.freesudoku

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "NumButtonClicked:"

class GameActivity : AppCompatActivity(), View.OnClickListener {

    var currentNum = 0

    var sudokuGrid = arrayOf(
        arrayOf(3,1,1,1,1,1,1,1,3),
        arrayOf(1,1,0,0,0,0,0,1,1),
        arrayOf(1,0,1,0,0,0,1,0,1),
        arrayOf(1,0,0,1,0,1,0,0,1),
        arrayOf(1,0,0,0,2,0,0,0,1),
        arrayOf(1,0,0,1,0,1,0,0,1),
        arrayOf(1,0,1,0,0,0,1,0,1),
        arrayOf(1,1,0,0,0,0,0,1,1),
        arrayOf(3,1,1,1,1,1,1,1,3),
    )

    var roomsContent = arrayOf(
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0)
    )

    val inputButtons = arrayOf(R.id.oneButton, R.id.twoButton, R.id.threeButton, R.id.fourButton,
        R.id.fiveButton, R.id.sixButton, R.id.sevenButton, R.id.eightButton, R.id.nineButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        for (button in inputButtons){
            var b: Button = findViewById(button)
            b.setOnClickListener(this)
        }

        val newGameButton: Button = findViewById(R.id.newGameButton)
        newGameButton.setOnClickListener{
            //Using New Game Button as temp to test data
            var stringOfSudokuGrid = ""
            for (room in sudokuGrid){
                for (cell in room){
                    stringOfSudokuGrid += "$cell "
                }
                stringOfSudokuGrid += "\n"
            }
            Log.d(TAG, "Sudoku Grid State:\n $stringOfSudokuGrid")
            var stringOfRoomsContent = ""
            for (room in roomsContent){
                for (cell in room){
                    stringOfRoomsContent += "$cell "
                }
                stringOfRoomsContent += "\n"
            }
            Log.d(TAG, "Room Content State:\n $stringOfRoomsContent")
        }

        //Initialize Callbacks for each of the RoomLayouts
        val rooms = arrayOf(R.id.topLeftRoom, R.id.topCenterRoom, R.id.topRightRoom,
        R.id.middleLeftRoom, R.id.middleCenterRoom, R.id.middleRightRoom,
        R.id.bottomLeftRoom, R.id.bottomCenterRoom, R.id.bottomRightRoom)
        for (room in rooms){
            var r: RoomLayout = findViewById(room)
            callbacks.add(r)
            roomsContent[rooms.indexOf(room)] = r.roomContents
        }

        //Initialize Puzzle
        for (row in 0..8){
            for(col in 0..8){
                val index = row % 3 * 3 + col % 3
                val entry = sudokuGrid[row][col]
                if (row in 0..2 && col in 0..2)
                    callbacks[0].updateRoomContent(index, entry)
                    //roomsContent[0][index] = entry
                if (row in 0..2 && col in 3..5)
                    callbacks[1].updateRoomContent(index, entry)
                    //roomsContent[1][index] = entry
                if (row in 0..2 && col in 6..8)
                    callbacks[2].updateRoomContent(index, entry)
                    //roomsContent[2][index] = entry
                if (row in 3..5 && col in 0..2)
                    callbacks[3].updateRoomContent(index, entry)
                    //roomsContent[3][index] = entry
                if (row in 3..5 && col in 3..5)
                    callbacks[4].updateRoomContent(index, entry)
                    //roomsContent[4][index] = entry
                if (row in 3..5 && col in 6..8)
                    callbacks[5].updateRoomContent(index, entry)
                    //roomsContent[5][index] = entry
                if (row in 6..8 && col in 0..2)
                    callbacks[6].updateRoomContent(index, entry)
                    //roomsContent[6][index] = entry
                if (row in 6..8 && col in 3..5)
                    callbacks[7].updateRoomContent(index, entry)
                    //roomsContent[7][index] = entry
                if (row in 6..8 && col in 6..8)
                    callbacks[8].updateRoomContent(index, entry)
                    //roomsContent[8][index] = entry
            }//for
        }//for

    }//onCreate

    interface Callback {
        fun updateCurrentNum(n: Int)
        fun updateRoomContent(index: Int, entry: Int)
    }
    private var callbacks: MutableList<Callback> = mutableListOf()

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
        for (button in inputButtons){
            var b: Button = findViewById(button)
            b.setBackgroundColor(resources.getColor(R.color.purple_500))
        }
        v.setBackgroundColor(resources.getColor(R.color.purple_700))
        Log.d(TAG, "CurrentNum: $currentNum")
        for (callback in callbacks) {
            callback.updateCurrentNum(this.currentNum)
        }
    }
}
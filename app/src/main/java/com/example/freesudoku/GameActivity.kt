package com.example.freesudoku

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.File

private const val TAG = "GameActivity:"

class GameActivity : AppCompatActivity(), View.OnClickListener {

    var currentNum = 0

    //Temp manual inputs
    var sudokuGrid = arrayOf(
        arrayOf(1,0,0,0,0,0,0,0,1),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,1,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(0,0,0,0,0,0,0,0,0),
        arrayOf(1,0,0,0,0,0,0,0,1)
    )
    var solutionGrid = arrayOf(
        arrayOf(1,1,1,1,1,1,1,1,1),
        arrayOf(1,1,1,1,1,1,1,1,1),
        arrayOf(1,1,1,1,1,1,1,1,1),
        arrayOf(1,1,1,1,1,1,1,1,1),
        arrayOf(1,1,1,1,1,1,1,1,1),
        arrayOf(1,1,1,1,1,1,1,1,1),
        arrayOf(1,1,1,1,1,1,1,1,1),
        arrayOf(1,1,1,1,1,1,1,1,1),
        arrayOf(1,1,1,1,1,1,1,1,1)
    )

    //roomsSolved[x] is true if the xth room is solved
    var roomsSolved = arrayOf(false, false, false, false, false, false, false, false, false)

    private val inputButtons = arrayOf(R.id.oneButton, R.id.twoButton, R.id.threeButton, R.id.fourButton,
        R.id.fiveButton, R.id.sixButton, R.id.sevenButton, R.id.eightButton, R.id.nineButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Check solution button updates the correctness of the solution, and notifies the player if they've solved the puzzle correctly
        val checkSolutionButton: Button = findViewById(R.id.checkSolutionButton)
        checkSolutionButton.setOnClickListener {
            for (i in 0..8){
                roomsSolved[i] = callbacks[i].checkRoomSolution()
            }
            if (!roomsSolved.contains(false)){
                //TODO: Make this toast a popup windows asking if they want to start a new game instead
                Toast.makeText(this, "PUZZLE SOLVED!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "PUZZLE NOT SOLVED!", Toast.LENGTH_SHORT).show()
            }
        }

        //TODO: Make 'New Game' Button generate a new board
        //TODO: Add a popup window to confirm new game
        val newGameButton: Button = findViewById(R.id.newGameButton)
        newGameButton.setOnClickListener {
            for (callback in callbacks){
                callback.resetRoom()
            }
        }

        //Initialize Callbacks for each of the RoomLayouts
        val rooms = arrayOf(R.id.topLeftRoom, R.id.topCenterRoom, R.id.topRightRoom,
        R.id.middleLeftRoom, R.id.middleCenterRoom, R.id.middleRightRoom,
        R.id.bottomLeftRoom, R.id.bottomCenterRoom, R.id.bottomRightRoom)
        for (room in rooms){
            var r: RoomLayout = findViewById(room)
            callbacks.add(r)
        }

        //TODO: Generate a new sudoku  when a new game is started (if there isn't one saved), and it's solution
        /* PSEUDOCODE
        * if (file.exists()) {
        *   readFromFile()
        * } else {
        *   generateNewSudokuPuzzle()
        * }*/

        //Initialize Puzzle by filling out roomsContent with data from sudokuGrid
        for (row in 0..8) {
            for(col in 0..8) {
                val index = row % 3 * 3 + col % 3
                val entry = sudokuGrid[row][col]
                val solution = solutionGrid[row][col]
                if (row in 0..2 && col in 0..2)
                    callbacks[0].updateRoomData(index, entry, solution)
                if (row in 0..2 && col in 3..5)
                    callbacks[1].updateRoomData(index, entry, solution)
                if (row in 0..2 && col in 6..8)
                    callbacks[2].updateRoomData(index, entry, solution)
                if (row in 3..5 && col in 0..2)
                    callbacks[3].updateRoomData(index, entry, solution)
                if (row in 3..5 && col in 3..5)
                    callbacks[4].updateRoomData(index, entry, solution)
                if (row in 3..5 && col in 6..8)
                    callbacks[5].updateRoomData(index, entry, solution)
                if (row in 6..8 && col in 0..2)
                    callbacks[6].updateRoomData(index, entry, solution)
                if (row in 6..8 && col in 3..5)
                    callbacks[7].updateRoomData(index, entry, solution)
                if (row in 6..8 && col in 6..8)
                    callbacks[8].updateRoomData(index, entry, solution)
            }//for
        }//for

        //Initialize onClickListeners for number buttons
        for (button in inputButtons) {
            var b: Button = findViewById(button)
            b.setOnClickListener(this)
        }

    }//onCreate

    fun generateNewSudokuSolution() {

    }

    //General Click Handler for Number Buttons
    override fun onClick(v: View) {
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
        //Log.d(TAG, "CurrentNum: $currentNum")
        for (callback in callbacks) {
            callback.updateCurrentNum(this.currentNum)
        }
    }

    //Callbacks allow RoomLayout to get data from GameActivity
    interface Callback {
        fun updateCurrentNum(n: Int)
        fun updateRoomData(index: Int, entry: Int, solution: Int)
        fun checkRoomSolution(): Boolean
        fun resetRoom()
    }
    private var callbacks: MutableList<Callback> = mutableListOf()

}
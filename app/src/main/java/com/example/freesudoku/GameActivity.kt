package com.example.freesudoku

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

private const val TAG = "GameActivity:"

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var currentNum = 0

    private var sudokuGrid = Array(9) {Array(9) {0} }
    private var solutionGrid = Array(9) {Array(9) {0} }

    //roomsSolved[x] is true if the xth room is solved
    private var roomsSolved = arrayOf(false, false, false, false, false, false, false, false, false)

    private val inputButtons = arrayOf(R.id.oneButton, R.id.twoButton, R.id.threeButton, R.id.fourButton,
        R.id.fiveButton, R.id.sixButton, R.id.sevenButton, R.id.eightButton, R.id.nineButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Check solution button updates the correctness of the solution, and notifies the player if they've solved the puzzle correctly
        val checkSolutionButton: Button = findViewById(R.id.checkSolutionButton)
        checkSolutionButton.setOnClickListener {
            for (i in 0..8){
                roomsSolved[i] = roomCallbacks[i].checkRoomSolution()
            }
            if (!roomsSolved.contains(false)){
                //TODO LATER: Make this toast a popup windows asking if they want to start a new game instead
                Toast.makeText(this, "PUZZLE SOLVED!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "PUZZLE NOT SOLVED!", Toast.LENGTH_SHORT).show()
            }
        }

        //TODO LATER: Add a popup window to confirm new game
        val newGameButton: Button = findViewById(R.id.newGameButton)
        newGameButton.setOnClickListener {
            for (callback in roomCallbacks){
                callback.resetRoom()
            }
            sudokuGrid = Array(9) {Array(9) {0} }
            solutionGrid = Array(9) {Array(9) {0} }
            generateNewSudokuSolution()
            generateNewSudokuPuzzle(35)
            initializeRoomData()
        }

        //Initialize Callbacks for each of the RoomLayouts
        val rooms = arrayOf(R.id.topLeftRoom, R.id.topCenterRoom, R.id.topRightRoom,
            R.id.middleLeftRoom, R.id.middleCenterRoom, R.id.middleRightRoom,
            R.id.bottomLeftRoom, R.id.bottomCenterRoom, R.id.bottomRightRoom)
        for (room in rooms){
            val r: RoomLayout = findViewById(room)
            roomCallbacks.add(r)
        }

        //TODO LATER: If a game doesn't already exist, generate one on start
        /*generateNewSudokuSolution()
        generateNewSudokuPuzzle(35)
        initializeRoomData()*/

        //Initialize onClickListeners for number buttons
        for (button in inputButtons) {
            val b: Button = findViewById(button)
            b.setOnClickListener(this)
        }

        //TODO: Remove Debug Section
        var stringOfSudokuGrid = ""
        for (row in 0..8) {
            for (col in 0..8) {
                stringOfSudokuGrid += "${sudokuGrid[row][col]}"
            }
            stringOfSudokuGrid += "\n"
        }
        Log.d(TAG, "Sudoku Grid:\n$stringOfSudokuGrid")

        var stringOfSolutionGrid = ""
        for (row in 0..8) {
            for (col in 0..8) {
                stringOfSolutionGrid += "${solutionGrid[row][col]}"
            }
            stringOfSolutionGrid += "\n"
        }
        Log.d(TAG, "Solution Grid:\n$stringOfSolutionGrid")

    }//onCreate

    private fun gridIsFilled(grid: Array<Array<Int>>): Boolean {
        for (row in 0..8) {
            for (col in 0..8) {
                if (grid[row][col] == 0) {
                    return false
                }
            }
        }
        return true
    }

    /* Returns a count of the solutions to a particular grid
    * 0 Means there are no solutions
    * 1 Means there is a unique solution
    * 2 means there is not a unique solution
    */
    private fun countSolutions(grid: Array<Array<Int>>, row: Int, col: Int, num: Int, numSolutions: Int): Int {
        //Check Row
        for (i in 0..8) {
            if (grid[row][i] == num)
                return numSolutions
        }
        //Check Col
        for (i in 0..8) {
            if (grid[i][col] == num)
                return numSolutions
        }
        //Check Room
        //identify the room
        val roomRow: Int = 3*(row/3)
        val roomCol: Int = 3*(col/3)
        //check if num is in the room
        for (r in roomRow..roomRow+2)
            for (c in roomCol..roomCol+2)
                if (grid[r][c] == num)
                    return numSolutions
        //Check for a solution
        //make a copy of grid
        val gridCopy = Array(9) {Array(9) {0} }
        for (r in 0..8) {
            for (c in 0..8) {
                gridCopy[r][c] = grid[r][c]
            }
        }
        //add the num to the copy, and recursively check every possible move until a solution is found
        gridCopy[row][col] = num
        //if the grid is full, then a solution was found
        if (gridIsFilled(gridCopy))
            return numSolutions + 1
        for (r in 0..8) {
            for (c in 0..8) {
                if (gridCopy[r][c] == 0) {
                    return (countSolutions(gridCopy, r, c, 1, numSolutions) +
                            countSolutions(gridCopy, r, c, 2, numSolutions) +
                            countSolutions(gridCopy, r, c, 3, numSolutions) +
                            countSolutions(gridCopy, r, c, 4, numSolutions) +
                            countSolutions(gridCopy, r, c, 5, numSolutions) +
                            countSolutions(gridCopy, r, c, 6, numSolutions) +
                            countSolutions(gridCopy, r, c, 7, numSolutions) +
                            countSolutions(gridCopy, r, c, 8, numSolutions) +
                            countSolutions(gridCopy, r, c, 9, numSolutions))
                }
                if (numSolutions > 1)
                    break
            }
            if (numSolutions > 1)
                break
        }
        //Grid was never filled
        return numSolutions
    }

    //Returns: If num can be placed at [row][col] without making an illegal move
    //TODO LATER: Implement countSolutions in this algorithm
    private fun validateNewNumPlacement(grid: Array<Array<Int>>, row: Int, col: Int, num: Int): Boolean {
        //Check Row
        for (i in 0..8) {
            if (grid[row][i] == num)
                return false
        }
        //Check Col
        for (i in 0..8) {
            if (grid[i][col] == num)
                return false
        }
        //Check Room
        //identify the room
        val roomRow: Int = 3*(row/3)
        val roomCol: Int = 3*(col/3)
        //check if num is in the room
        for (r in roomRow..roomRow+2)
            for (c in roomCol..roomCol+2)
                if (grid[r][c] == num)
                    return false
        //Check for a solution
        //make a copy of grid
        val gridCopy = Array(9) {Array(9) {0} }
        for (r in 0..8) {
            for (c in 0..8) {
                gridCopy[r][c] = grid[r][c]
            }
        }
        //add the num to the copy, and recursively check every possible move until a solution is found
        gridCopy[row][col] = num
        //if the grid is full, then a solution was found
        if (gridIsFilled(gridCopy))
            return true
        for (r in 0..8) {
            for (c in 0..8) {
                if (gridCopy[r][c] == 0) {
                    return (validateNewNumPlacement(gridCopy, r, c, 1) ||
                            validateNewNumPlacement(gridCopy, r, c, 2) ||
                            validateNewNumPlacement(gridCopy, r, c, 3) ||
                            validateNewNumPlacement(gridCopy, r, c, 4) ||
                            validateNewNumPlacement(gridCopy, r, c, 5) ||
                            validateNewNumPlacement(gridCopy, r, c, 6) ||
                            validateNewNumPlacement(gridCopy, r, c, 7) ||
                            validateNewNumPlacement(gridCopy, r, c, 8) ||
                            validateNewNumPlacement(gridCopy, r, c, 9)
                            )
                }
            }
        }
        //Grid was never filled
        return false
    }

    //Returns: countSolutions after removing num from grid
    private fun validateNumRemoval(grid: Array<Array<Int>>, row: Int, col: Int, num: Int): Int {

        //create a copy of grid
        val gridCopy = Array(9){Array(9) {0} }
        for(r in 0..8) {
            for (c in 0..8) {
                gridCopy[r][c] = grid[r][c]
            }
        }

        //remove the number from the copy
        gridCopy[row][col] = 0
        //Check all possible solutions
        val solutions = countSolutions(gridCopy, row, col, num, 0)
        grid[row][col] = num
        return solutions

    }

    //Fills solutionGrid with a valid sudoku solution
    private fun generateNewSudokuSolution() {
        //TODO LATER: Make the rol & col selections random
        for (row in 0..8) {
            for (col in 0..8) {
                val numsToTry = mutableListOf(1,2,3,4,5,6,7,8,9)
                while (numsToTry.isNotEmpty()) {
                    val randNum = numsToTry[(0 until numsToTry.size).random()]
                    if (solutionGrid[row][col] == 0 && validateNewNumPlacement(solutionGrid, row, col, randNum)) {
                        //Set this to keep the solution
                        solutionGrid[row][col] = randNum
                    }
                    numsToTry.remove(randNum)
                }//while numsToTry
            }
        }
    }

    //Removes Entries from sudokuGrid until 35 have been removed, but there is only 1 solution
    private fun generateNewSudokuPuzzle(numToRemove: Int) {
        //Copy the solution to the grid so numbers can be removed
        for (row in 0..8) {
            for (col in 0..8) {
                sudokuGrid[row][col] = solutionGrid[row][col]
            }
        }
        //Make a list of 0..80
        val remainingIndices = MutableList(81){it + 1}
        //track number of attempts to remove a number
        var attemptsMade = 0
        while (remainingIndices.size > 81-numToRemove){
            val randCell: Int = remainingIndices[(0 until remainingIndices.size-1).random()]
            val randRow: Int = randCell/9
            val randCol: Int = randCell%9
            val solutions = validateNumRemoval(sudokuGrid, randRow, randCol, sudokuGrid[randRow][randCol])
            if (solutions == 1){
                //remove the value, and the index
                sudokuGrid[randRow][randCol] = 0
                remainingIndices.remove(randCell)
                //reset attempts
                attemptsMade = 0
            }
            attemptsMade++
            //if too many attempts made, give up
            if(attemptsMade >= (5*remainingIndices.size)) {
                break
            }
        }

    }

    private fun initializeRoomData() {
        //Initialize Puzzle by filling out roomsContent with data from sudokuGrid
        for (row in 0..8) {
            for(col in 0..8) {
                val index = row % 3 * 3 + col % 3
                val entry = sudokuGrid[row][col]
                val solution = solutionGrid[row][col]
                val room: Int = 3*(row/3) + (col/3)
                roomCallbacks[room].setRoomData(index, entry, solution)
            }//for
        }//for
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
            val b: Button = findViewById(button)
            b.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.purple_500))
        }
        v.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.purple_700))
        //Log.d(TAG, "CurrentNum: $currentNum")
        for (callback in roomCallbacks) {
            callback.updateCurrentNum(this.currentNum)
        }
    }

    //Callbacks allow RoomLayout to get data from GameActivity
    interface Callback {
        fun updateCurrentNum(currentNum: Int)
        fun setRoomData(index: Int, entry: Int, solution: Int)
        fun checkRoomSolution(): Boolean
        fun resetRoom()
    }
    private var roomCallbacks: MutableList<Callback> = mutableListOf()

}
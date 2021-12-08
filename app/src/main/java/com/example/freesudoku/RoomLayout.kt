package com.example.freesudoku

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.TextView
import java.io.*

class RoomLayout @JvmOverloads
    constructor(private val ctx: Context, private val attributeSet: AttributeSet? = null, private val defStyleAttr: Int = 0)
    : GridLayout(ctx, attributeSet, defStyleAttr), GameActivity.Callback {

    private val tag = this.context.resources.getResourceEntryName(this.id).toString()

    private var selectedNum = 0
    private var roomContents = Array(9){0}
    private var roomSolution = Array(9){0}
    //Starting Hint: 1 if cell was a starting hint, 0 otherwise
    private var lockedCells = Array(9){0}
    private var cells = arrayOf(R.id.topLeft, R.id.topCenter, R.id.topRight,
        R.id.middleLeft, R.id.middleCenter, R.id.middleRight,
        R.id.bottomLeft, R.id.bottomCenter, R.id.bottomRight)
    private val filename = this.context.resources.getResourceEntryName(this.id).toString() + "Data.txt"
    private val file = File(ctx.filesDir, filename)

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.room_layout, this)

        if (!file.exists())
            file.createNewFile()
        if (file.exists()) {
            val fileContents = FileInputStream(file).bufferedReader().use { it.readText() }
            var index = 0
            for (char in fileContents){
                val num = Character.getNumericValue(char)
                if (index in 0..8)
                    roomContents[index] = num
                if (index in 9..17)
                    roomSolution[index-9] = num
                if (index in 18..26)
                    lockedCells[index-18] = num
                index++
            }
            Log.d(tag, "fileContents: $fileContents")
            var roomContentsString = ""
            var roomSolutionString = ""
            var startingHintString = ""
            for (i in 0..8) {
                roomContentsString += roomContents[i]
                roomSolutionString += roomSolution[i]
                startingHintString += lockedCells[i]
            }
            Log.d(tag, "roomContents: $roomContentsString")
            Log.d(tag, "roomSolution: $roomSolutionString")
            Log.d(tag, "startingHint: $startingHintString")
        } else {
            Log.e(tag, "file does not exist")
        }

    }//init

    //Callback Functions
    override fun updateCurrentNum(currentNum: Int){
        this.selectedNum = currentNum
        //Log.d("RoomLog:", "Updated Current Num?: $selectedNum")
    }

    override fun setRoomData(index: Int, entry: Int, solution: Int){
        roomContents[index] = entry
        roomSolution[index] = solution
        val cell: TextView = findViewById(cells[index])
        if (roomContents[index] != 0) {
            cell.text = entry.toString()
            lockedCells[index] = 1
            cell.setTextColor(Color.BLACK)
            cell.setOnClickListener(null)
        } else {
            cell.setTextColor(Color.GRAY)
        }
        var convertedData = ""
        for (i in 0..8) {
            convertedData += roomContents[i].toString()
        }
        for (i in 0..8) {
            convertedData += roomSolution[i].toString()
        }
        for (i in 0..8) {
            convertedData += lockedCells[i].toString()
        }
        file.writeText(convertedData)
    }

    override fun onCreateGameActivity() {
        for (cell in cells){
            val c: TextView = findViewById(cell)
            if(roomContents[cells.indexOf(cell)] != 0)
                c.text = roomContents[cells.indexOf(cell)].toString()
            c.setOnClickListener {
                if (selectedNum != 0) {
                    //Update room data
                    c.text = selectedNum.toString()
                    roomContents[cells.indexOf(cell)] = selectedNum
                    //Save Data to file
                    var convertedData = ""
                    for (i in 0..8) {
                        convertedData += roomContents[i].toString()
                    }
                    for (i in 0..8) {
                        convertedData += roomSolution[i].toString()
                    }
                    for (i in 0..8) {
                        convertedData += lockedCells[i].toString()
                    }
                    file.writeText(convertedData)
                }
            }//onClickListener
            if(lockedCells[cells.indexOf(cell)] == 1) {
                c.setTextColor(Color.BLACK)
                c.setOnClickListener(null)
            }
        }//for
    }

    override fun checkRoomSolution(): Boolean {
        for(i in 0..8){
            if (roomContents[i] != roomSolution[i])
                return false
        }
        return true
    }

    override fun resetRoom() {
        roomContents = Array(9){0}
        roomSolution = Array(9){0}
        lockedCells = Array(9){0}
        file.writeText("")
        for (cell in cells) {
            val c: TextView = findViewById(cell)
            c.text = ""
            c.setOnClickListener {
                if (selectedNum != 0) {
                    //Update room data
                    c.text = selectedNum.toString()
                    roomContents[cells.indexOf(cell)] = selectedNum
                    //Save Data to file
                    var convertedData = ""
                    for (i in 0..8) {
                        convertedData += roomContents[i].toString()
                    }
                    for (i in 0..8) {
                        convertedData += roomSolution[i].toString()
                    }
                    for (i in 0..8) {
                        convertedData += lockedCells[i].toString()
                    }
                    file.writeText(convertedData)
                }
            }//onClickListener
        }
    }
}
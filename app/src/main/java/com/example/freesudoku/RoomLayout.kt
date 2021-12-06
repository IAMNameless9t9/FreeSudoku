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
    private var roomContents = arrayOf(0,0,0,0,0,0,0,0,0)
    private var roomSolution = arrayOf(0,0,0,0,0,0,0,0,0)
    private var cells = arrayOf(R.id.topLeft, R.id.topCenter, R.id.topRight,
        R.id.middleLeft, R.id.middleCenter, R.id.middleRight,
        R.id.bottomLeft, R.id.bottomCenter, R.id.bottomRight)
    private val filename = this.context.resources.getResourceEntryName(this.id).toString() + "Data.txt"
    private val file = File(ctx.filesDir, filename)

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.room_layout, this)

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
                index++
            }
            //Log.d(tag, "fileContents: $fileContents")
            var roomContentsString = ""
            var roomSolutionString = ""
            for (i in 0..8) {
                roomContentsString += roomContents[i]
                roomSolutionString += roomSolution[i]
            }
            //Log.d(tag, "roomContents: $roomContentsString")
            //Log.d(tag, "roomSolution: $roomSolutionString")
        } else {
            Log.e(tag, "file does not exist")
        }

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
                    file.writeText(convertedData)
                }//if
            }//onClickListener
        }//for
    }

    override fun updateCurrentNum(currentNum: Int){
        this.selectedNum = currentNum
        //Log.d("RoomLog:", "Updated Current Num?: $selectedNum")
    }

    override fun updateRoomData(index: Int, entry: Int, solution: Int){
        roomContents[index] = entry
        roomSolution[index] = solution
        val cell: TextView = findViewById(cells[index])
        if (roomContents[index] != 0) {
            cell.text = entry.toString()
            cell.setTextColor(Color.BLACK)
            cell.setOnClickListener(null)
        }
    }

    override fun checkRoomSolution(): Boolean {
        for(i in 0..8){
            if (roomContents[i] != roomSolution[i])
                return false
        }
        return true
    }

    override fun resetRoom() {
        for (i in 0..8) {
            roomContents[i] = 0
            file.writeText("")
        }
        for (cell in cells) {
            val c: TextView = findViewById(cell)
            c.text = ""
        }
    }
}
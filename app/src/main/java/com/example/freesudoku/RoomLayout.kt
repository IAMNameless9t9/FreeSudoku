package com.example.freesudoku

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView

class RoomLayout @JvmOverloads
    constructor(private val ctx: Context, private val attributeSet: AttributeSet? = null, private val defStyleAttr: Int = 0)
    : GridLayout(ctx, attributeSet, defStyleAttr), GameActivity.Callback {

    private var selectedNum = 0
    var roomContents = arrayOf(0,0,0,0,0,0,0,0,0)
    var roomSolution = arrayOf(0,0,0,0,0,0,0,0,0)
    var cells = arrayOf(R.id.topLeft, R.id.topCenter, R.id.topRight,
        R.id.middleLeft, R.id.middleCenter, R.id.middleRight,
        R.id.bottomLeft, R.id.bottomCenter, R.id.bottomRight)

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.room_layout, this)

        for (cell in cells){
            var c: TextView = findViewById(cell)
                c.setOnClickListener {
                    //Log.d("RoomLog", "Room/Cell: " + this.context.resources.getResourceEntryName(this.id) + " " + c.context.resources.getResourceEntryName(c.id) + " | Selected Num: " + selectedNum)
                    if (selectedNum != 0) {
                        c.text = selectedNum.toString()
                        roomContents[cells.indexOf(cell)] = selectedNum
                        var stringOfRoomContents = ""
                        for (n in roomContents){
                            stringOfRoomContents += "${n.toString()}, "
                        }
                        Log.d("RoomContentsLog", "Contents: [$stringOfRoomContents]")
                        var stringOfRoomSolution = ""
                        for (n in roomSolution){
                            stringOfRoomSolution += "${n.toString()}, "
                        }
                        Log.d("RoomSolutionLog", "Contents: [$stringOfRoomSolution]")
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
        for (cell in cells) {
            var c: TextView = findViewById(cell)
            if (roomContents[cells.indexOf(cell)] != 0) {
                c.text = roomContents[cells.indexOf(cell)].toString()
                c.setTextColor(Color.BLACK)
                c.setOnClickListener(null)
            }
        }
    }

    override fun checkRoomSolution(): Boolean {
        for(i in 0..8){
            if (roomContents[i] != roomSolution[i])
                return false;
        }
        return true;
    }

}
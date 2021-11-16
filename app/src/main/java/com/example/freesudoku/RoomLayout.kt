package com.example.freesudoku

import android.content.Context
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
    : GridLayout(ctx, attributeSet, defStyleAttr), GameActivity.Callbacks {

    private var selectedNum = 0

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.room_layout, this)

        var cells = arrayOf(R.id.topLeft, R.id.topCenter, R.id.topRight,
        R.id.middleLeft, R.id.middleCenter, R.id.middleRight,
        R.id.bottomLeft, R.id.bottomCenter, R.id.bottomRight)

        for (cell in cells){
            var c: TextView = findViewById(cell)
            c.setOnClickListener {
                Log.d("RoomLog", "Room/Cell: " + this.context.resources.getResourceEntryName(this.id) +
                        " " + c.context.resources.getResourceEntryName(c.id) + " | Selected Num: " + selectedNum)
                if (selectedNum != 0) {
                    c.text = selectedNum.toString()
                }//if
            }//onClickListener
        }//for
    }

    override fun updateCurrentNum(currentNum: Int) {
        this.selectedNum = currentNum
        Log.d("RoomLog:", "Updated Current Num?: $selectedNum")
    }

}
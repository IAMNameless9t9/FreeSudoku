package com.example.freesodoku

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView

class RoomLayout @JvmOverloads
    constructor(private val ctx: Context, private val attributeSet: AttributeSet? = null, private val defStyleAttr: Int = 0)
    : GridLayout(ctx, attributeSet, defStyleAttr) {

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.room_layout, this)
        initilizeVariables()
    }

    fun initilizeVariables(){

        var cell1 : TextView = findViewById(R.id.topLeft)
        var cell2 : TextView = findViewById(R.id.topCenter)
        var cell3 : TextView = findViewById(R.id.topRight)
        var cell4 : TextView = findViewById(R.id.middleLeft)
        var cell5 : TextView = findViewById(R.id.middleCenter)
        var cell6 : TextView = findViewById(R.id.middleRight)
        var cell7 : TextView = findViewById(R.id.bottomLeft)
        var cell8 : TextView = findViewById(R.id.bottomCenter)
        var cell9 : TextView = findViewById(R.id.bottomRight)

        var cells = arrayOf(cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9)
    }

}
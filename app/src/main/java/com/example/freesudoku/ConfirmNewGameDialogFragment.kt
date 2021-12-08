package com.example.freesudoku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class ConfirmNewGameDialogFragment: DialogFragment() {

    interface NewGameDialogListener {
        fun onFinishDialog(startNewGame: Boolean)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.dialog_fragment_confirm_new_game, container, false)
        val callBack = activity as NewGameDialogListener?

        val confirmButton: Button = rootView.findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener {
            callBack?.onFinishDialog(true)
            dismiss()
        }
        val cancelButton: Button = rootView.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            dismiss()
        }

        return rootView
    }

}
package com.example.scanocular.activity.scan

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.scanocular.R

class ScanTOSDialog(private val context: Context) {

    fun show() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.alert_dialog_scan_tos, null)

        // Access views from the custom layout
        val titleTextView: TextView = dialogView.findViewById(R.id.customDialogTitle)
        val inputEditText: EditText = dialogView.findViewById(R.id.customDialogInput)
        val okButton: Button = dialogView.findViewById(R.id.customDialogButton)

        // Customize the views if needed
        titleTextView.text = "Custom Title"

        // Set up the AlertDialog
        builder.setView(dialogView)
        val dialog = builder.create()

        // Set click listener for the OK button
        okButton.setOnClickListener {
            val userInput = inputEditText.text.toString()
            // Handle the user input or perform any action
            // ...

            // Dismiss the dialog
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }

}
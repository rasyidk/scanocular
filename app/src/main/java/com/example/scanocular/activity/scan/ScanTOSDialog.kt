package com.example.scanocular.activity.scan

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import com.example.scanocular.R
import com.example.scanocular.activity.main.MainActivity

class ScanTOSDialog(private val context: Context) {

    fun show() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.alert_dialog_scan_tos, null)

        // Access views from the custom layout
        val titleTextView: TextView = dialogView.findViewById(R.id.customDialogTitle)
        val okButton: Button = dialogView.findViewById(R.id.customDialogButton)
        val cancelButton : Button = dialogView.findViewById(R.id.tos_bt_cancel)

        // Set up the AlertDialog
        builder.setView(dialogView)
        val dialog = builder.create()

        // Set click listener for the OK button
        okButton.setOnClickListener {

            val customDialog = ScanTutorDialog(context)
            customDialog.show()

            dialog.dismiss()
        }

        cancelButton.setOnClickListener{

            val intent = Intent(context, MainActivity::class.java)

            // Add any additional data to the intent if needed
            // intent.putExtra("key", "value")

            // Start the activity
            context.startActivity(intent)

            // Dismiss the dialog if needed
            dialog.dismiss()

        }

        // Show the dialog
        dialog.show()
    }

}
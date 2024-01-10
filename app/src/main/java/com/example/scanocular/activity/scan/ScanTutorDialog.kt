package com.example.scanocular.activity.scan

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.example.scanocular.R
import com.example.scanocular.activity.main.MainActivity

class ScanTutorDialog(private val context: Context) {

    fun show() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.alert_dialog_tutor, null)

        val btLanjutkan: Button = dialogView.findViewById(R.id.tutor_bt_lanjutkan)


        // Set up the AlertDialog
        builder.setView(dialogView)
        val dialog = builder.create()

        btLanjutkan.setOnClickListener{
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }
}
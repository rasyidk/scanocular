package com.example.scanocular.activity.scan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanocular.R
import com.example.scanocular.databinding.ActivityScanBinding
import com.example.scanocular.databinding.ActivityScanResultBinding

class ScanResultActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val diagnosa = intent.getStringExtra("diagnosa")

        binding.scanResTvHasilTes.text = diagnosa
    }
}
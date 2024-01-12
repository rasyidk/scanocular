package com.example.scanocular.activity.scan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.scanocular.databinding.ActivityScanResultBinding
import com.example.scanocular.util.SharedPreferencesManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ScanResultActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val diagnosa = intent.getStringExtra("diagnosa")
        val sharedPreferences = SharedPreferencesManager.getUserData(this)
        if (sharedPreferences != null) {
            binding.scanResTvName.text = sharedPreferences.name.toString()
            binding.scanResTvTglLahir.text = sharedPreferences.tanggalLahir.toString()
            binding.scanResTvNIK.text = sharedPreferences.nIK.toString()
            binding.scanResTvAlamat.text = sharedPreferences.alamat.toString()
        }
        binding.scanResTvTanggalTes.text = getDateNow()
        binding.scanResTvHasilTes.text = diagnosa


        getDateNow()
    }

    private fun getDateNow(): String? {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return currentDateTime.format(formatter)
    }
}
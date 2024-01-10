package com.example.scanocular.activity.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import com.example.scanocular.R
import com.example.scanocular.activity.login.LoginActivity
import com.example.scanocular.api.user.UserApi
import com.example.scanocular.databinding.ActivityRegisterBinding
import com.example.scanocular.model.users.LoginRequest
import com.example.scanocular.model.users.LoginResponse
import com.example.scanocular.model.users.RegisterResponse
import com.example.scanocular.model.users.User
import com.example.scanocular.util.DatePicker
import com.example.scanocular.util.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

class RegisterActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityRegisterBinding
    private var dates= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener(){
        with(binding){
            buttonLog.setOnClickListener(){
                if (nikField.text.toString().isEmpty()){
                    emailField.error = "NIK harus diisi"
                    return@setOnClickListener
                }
                if (nikField.text.toString().length != 16){
                    nikField.error = "NIK harus 16 karakter"
                    return@setOnClickListener
                }
                if (namaField.text.toString().isEmpty()){
                    namaField.error = "Nama harus diisi"
                    return@setOnClickListener
                }
                if (ttlField.text.toString().isEmpty()){
                    ttlField.error = "Tanggal lahir harus diisi"
                    return@setOnClickListener
                }
                if (emailField.text.toString().isEmpty()){
                    emailField.error = "Email harus diisi"
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailField.text).matches()){
                    emailField.error = "Email tidak valid"
                    return@setOnClickListener
                }
                if (alamatField.text.toString().isEmpty()){
                    emailField.error = "Alamat harus diisi"
                    return@setOnClickListener
                }

                if (passwordField.text.toString().length < 8){
                    passwordField.error = "Password minimal 8 karakter"
                    return@setOnClickListener
                }

                if (passwordField.text.toString() != confirmField.text.toString()){
                    confirmField.error = "Password tidak cocok"
                    return@setOnClickListener
                }

                register(User(nikField.text.toString(),
                    passwordField.text.toString(),
                    namaField.text.toString(),
                    emailField.text.toString(),
                    dates,
                    alamatField.text.toString()))
            }

            regtologin.setOnClickListener{
                startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
                finish()
            }

            btPickDate.setOnClickListener {
                val mDatePickerDialogFragment: DatePicker = DatePicker()
                mDatePickerDialogFragment.show(supportFragmentManager, "DATE PICK")
            }
        }
    }

    private fun register(user:User){
        try {
            val apiService = RetrofitClient.instance.create(UserApi::class.java)
            val call = apiService.register(user)
            call.enqueue(object : Callback<RegisterResponse>{
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                   if (response.isSuccessful){
                       startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
                       finish()
                       Toast.makeText(this@RegisterActivity,"Akun anda berhasil dibuat",
                           Toast.LENGTH_SHORT).show()
                   }else{
                       Toast.makeText(this@RegisterActivity,"Terjadi Kesalahan, Silahkan Coba Lagi",
                           Toast.LENGTH_SHORT).show()
                   }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity,"Terjadi Kesalahan, Silahkan Coba Lagi",
                        Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e:Exception){
            Toast.makeText(this@RegisterActivity,"Terjadi Kesalahan, Silahkan Coba Lagi",
                Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onDateSet(view: android.widget.DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val mCalendar = Calendar.getInstance()
        val currentDate: String = SimpleDateFormat("yyyy-MM-dd").format(mCalendar.time)
        mCalendar[Calendar.YEAR] = year
        mCalendar[Calendar.MONTH] = month
        mCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val selectedDate = DateFormat.getDateInstance(DateFormat.LONG).format(mCalendar.time)
        val text : TextView = findViewById(R.id.ttl_field)
        text.text = selectedDate
        dates = currentDate
    }
}
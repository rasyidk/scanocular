package com.example.scanocular.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.scanocular.R
import com.example.scanocular.activity.main.MainActivity
import com.example.scanocular.activity.register.RegisterActivity
import com.example.scanocular.api.user.UserApi
import com.example.scanocular.databinding.ActivityLoginBinding
import com.example.scanocular.model.users.LoginRequest
import com.example.scanocular.model.users.LoginResponse
import com.example.scanocular.util.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener(){
        with(binding){
            buttonLog.setOnClickListener(){
//                login()
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            }

            logintoreg.setOnClickListener{
                startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))

            }
        }
    }

    private fun login(){
        with(binding){
            try {
                val email = emailField.text.toString()
                val password = passwordField.text.toString()
                val apiService = RetrofitClient.instance.create(UserApi::class.java)
                val req = LoginRequest(email,password)
                val call = apiService.login(req)

                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(this@LoginActivity,"Login Berhasil",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@LoginActivity,"Gagal Login",Toast.LENGTH_SHORT).show()
                        }
                        Log.d("Response Headers", response.headers().toString())
                        Log.d("Response Body", response.body().toString())
                        Log.d("Response Body", response.raw().toString())
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("EXP",t.message.toString())
                    }
                })

            }catch (e:Exception){
                Log.d("EXP",e.message.toString())

            }
        }
    }
}
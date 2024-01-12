package com.example.scanocular.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.scanocular.R
import com.example.scanocular.activity.main.MainActivity
import com.example.scanocular.activity.register.RegisterActivity
import com.example.scanocular.api.user.UserApi
import com.example.scanocular.databinding.ActivityLoginBinding
import com.example.scanocular.model.users.LoginRequest
import com.example.scanocular.model.users.LoginResponse
import com.example.scanocular.model.users.User
import com.example.scanocular.util.RetrofitClient
import com.example.scanocular.util.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = SharedPreferencesManager.getUserData(this)
        if (name !== null){
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            finish()
        }else{
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setupListener()
        }
    }

    private fun setupListener(){
        with(binding){
            buttonLog.setOnClickListener(){
                if (emailField.text.toString().isEmpty()){
                    emailField.error = "Email harus diisi"
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailField.text).matches()){
                    emailField.error = "Email tidak valid"
                    return@setOnClickListener
                }
                if (passwordField.text.toString().length < 8){
                    passwordField.error = "Password minimal 8 karakter"
                    return@setOnClickListener
                }
                login()
            }
            logintoreg.setOnClickListener{
                startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
                finish()
            }
        }
    }

    private fun login(){
        with(binding){
            try {
                val email = emailField.text.toString()
                val password = passwordField.text.toString()
                val apiService = RetrofitClient.instance.create(UserApi::class.java)
                val req = LoginRequest(password,email)
                val call = apiService.login(req)
                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful){
                            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                            val data = response.body()!!.data
                            val user = User(data!!.userId!!.toInt(),data.nIK,"",data.name,data.email,data.ttl,data.alamat)
                            SharedPreferencesManager.saveUserData(this@LoginActivity,user)
                            finish()
                        }else{
                            Toast.makeText(this@LoginActivity,"Terjadi Kesalahan, Silahkan Coba Lagi",Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity,"Terjadi Kesalahan, Silahkan Coba Lagi",Toast.LENGTH_SHORT).show()
                    }
                })
            }catch (e:Exception){
                Toast.makeText(this@LoginActivity,"Terjadi Kesalahan, Silahkan Coba Lagi",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
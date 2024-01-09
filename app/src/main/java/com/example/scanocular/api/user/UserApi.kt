package com.example.scanocular.api.user

import com.example.scanocular.model.scan.ScanUploadRequest
import com.example.scanocular.model.users.LoginRequest
import com.example.scanocular.model.users.LoginResponse
import com.example.scanocular.model.users.RegisterResponse
import com.example.scanocular.model.users.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {
    @POST("users/signup")
    fun register(@Body request: User): Call<RegisterResponse>

    @Headers("Accept: application/json")
    @POST("users/signin")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
package com.example.scanocular.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val instance: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)   // Increase read timeout to 60 seconds
            .connectTimeout(60, TimeUnit.SECONDS) // Increase connect timeout to 60 seconds
            .writeTimeout(60, TimeUnit.SECONDS)   // Increase write timeout to 60 seconds
            .build()

        Retrofit.Builder()
            .baseUrl("https://scanocular.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}

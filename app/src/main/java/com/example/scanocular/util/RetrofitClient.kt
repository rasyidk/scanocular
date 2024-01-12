package com.example.scanocular.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
object RetrofitClient {
    val instance: Retrofit by lazy {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)   // Increase read timeout to 60 seconds
            .connectTimeout(60, TimeUnit.SECONDS) // Increase connect timeout to 60 seconds
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor(TrailingSlashInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl("https://scanocular.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}

package com.example.scanocular.util

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class TrailingSlashInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val originalUrl: HttpUrl = originalRequest.url

        if (!originalUrl.toString().endsWith("/")) {
            val newUrl = originalUrl.newBuilder().addPathSegment("").build()
            val newRequest: Request = originalRequest.newBuilder().url(newUrl).build()
            return chain.proceed(newRequest)
        }

        return chain.proceed(originalRequest)
    }
}
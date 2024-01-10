package com.example.scanocular.api.scan

import com.example.scanocular.model.scan.ScanResponse
import com.example.scanocular.model.scan.ScanUploadRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ScanAPI {
    @POST("pemeriksaan/cekmata/katarak")
    fun uploadImage(@Body request: ScanUploadRequest): Call<ScanResponse>
}
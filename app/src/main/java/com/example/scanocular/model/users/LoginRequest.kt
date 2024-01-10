package com.example.scanocular.model.users

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginRequest(

	@field:SerializedName("password")
	val password: String="",

	@field:SerializedName("email")
	val email: String=""
)

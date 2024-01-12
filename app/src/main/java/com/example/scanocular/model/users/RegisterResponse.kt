package com.example.scanocular.model.users

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("NIK")
	val nIK: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("ttl")
	val ttl: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)

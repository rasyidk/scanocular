package com.example.scanocular.model.users

import com.google.gson.annotations.SerializedName

data class User(

	val id:Int=0,

	@field:SerializedName("NIK")
	val nIK: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)

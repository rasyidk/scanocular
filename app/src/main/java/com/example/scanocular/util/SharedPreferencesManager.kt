package com.example.scanocular.util

import android.content.Context
import com.example.scanocular.model.users.User
import com.google.gson.Gson

object SharedPreferencesManager {

    private const val PREF_NAME = "user_preferences"
    private const val KEY_USER_DATA = "user_data"
    fun saveUserData(context: Context, userData: User) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val userDataJson = Gson().toJson(userData)

        editor.putString(KEY_USER_DATA, userDataJson)
        editor.apply()
    }

    fun getUserData(context: Context): User? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString(KEY_USER_DATA, null)

        return if (userDataJson != null) {
            Gson().fromJson(userDataJson, User::class.java)
        } else {
            null
        }
    }

}
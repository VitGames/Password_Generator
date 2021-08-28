package com.vitgames.passworkd_gen.api

import com.google.gson.annotations.SerializedName

class PasswordModel {

    @SerializedName("data")
    private val password: String = ""

    fun getPassword(): String {
        return password
    }
}
package com.vitgames.passworkd_gen.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInteractor {
   // private var retrofit: Retrofit? = null
    private var baseURl: String = "https://passwordinator.herokuapp.com/"

    interface ApiInterface {
        @GET("generate")
        fun getPassword(
            @Query("num") hasNum: Boolean,
            @Query("char") hasChar: Boolean,
            @Query("caps") hasCaps: Boolean,
            @Query("len") passLenght: Int
        ): Call<PasswordModel>
    }

    fun getClient(): Retrofit {
       // if (retrofit == null) {
        // }
        return Retrofit.Builder()
             .baseUrl(baseURl)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
    }
}
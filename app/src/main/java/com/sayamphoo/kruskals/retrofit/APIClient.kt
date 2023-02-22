package com.sayamphoo.kruskals.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sayamphoo.github.io/CPE200/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
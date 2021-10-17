package com.example.coffeshop.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    fun getApi(): CoffeeApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://616b1a3416e7120017fa1219.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CoffeeApi::class.java)
    }
}
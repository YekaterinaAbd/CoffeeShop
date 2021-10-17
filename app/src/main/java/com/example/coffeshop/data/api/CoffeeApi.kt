package com.example.coffeshop.data.api

import com.example.coffeshop.data.model.Order
import com.example.coffeshop.data.model.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeeApi {

    @GET("products")
    fun getProducts(): Call<List<Product>>

    @GET("order")
    fun getOrders(): Call<List<Order>>

    @GET("products/{id}")
    fun getProductById(
        @Path("id") id: String
    ): Call<Order>

    @POST("order")
    fun createOrder(
        @Body order: Order
    ): Call<Order>
}
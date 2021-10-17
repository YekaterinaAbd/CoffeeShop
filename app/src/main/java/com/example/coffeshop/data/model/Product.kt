package com.example.coffeshop.data.model

data class Product(
    val title: String,
    val description: String,
    val price: Int,
    val id: String
)

data class OrderItem(
    val order_id: String,
    val quantity: Int,
    val total_price: Double,
    val id: String,
    val product: Product
)

data class Order(
    val customer: String,
    val created_at: String,
    val total_price: Double,
    var status: String,
    val order_item: OrderItem,
    val id: String,
    val address: String
)

enum class Status {
    FORMATTING,
    ON_WAY,
    DELIVERED
}
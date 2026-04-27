package com.example.casapuranontox.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val subcategory: String,
    val whyItsClean: String,
    val replaces: String,
    val certifications: List<String>,
    val imageUrl: String,
    val productUrl: String
)

@Serializable
data class ProductsResponse(
    val products: List<Product>
)

@Serializable
data class JsonBinResponse(
    val record: ProductsResponse
)

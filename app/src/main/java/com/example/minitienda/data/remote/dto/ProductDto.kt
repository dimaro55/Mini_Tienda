package com.example.minitienda.data.remote.dto

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String,
    val images: List<String>
)
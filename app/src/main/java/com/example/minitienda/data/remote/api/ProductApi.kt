package com.example.minitienda.data.remote.api

import com.example.minitienda.data.remote.dto.ProductDto
import com.example.minitienda.data.remote.dto.ProductResponseDto
import com.example.minitienda.utils.PRODUCTS
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET(PRODUCTS)
    suspend fun getProducts(): ProductResponseDto

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductDto
}